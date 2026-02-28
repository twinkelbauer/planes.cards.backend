package cards.planes.backend.flight

import cards.planes.backend.config.LufthansaProperties
import org.openapitools.client.model.FlightAggregate
import org.openapitools.client.model.Leg
import org.openapitools.client.model.PeriodOfOperation
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Service
class FlightIngestionService(
    private val properties: LufthansaProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val ssimDateFormat = DateTimeFormatter.ofPattern("ddMMMyy", Locale.ENGLISH)

    fun ingest(aggregates: List<FlightAggregate>): Int {
        var count = 0
        transaction {
            for (aggregate in aggregates) {
                count += expandAndStore(aggregate)
            }
        }
        return count
    }

    private fun expandAndStore(aggregate: FlightAggregate): Int {
        val airline = aggregate.airline ?: return 0
        val flightNumber = aggregate.flightNumber ?: return 0
        val period = aggregate.periodOfOperationUTC ?: return 0
        val legs = aggregate.legs ?: return 0

        val dates = expandPeriod(period)
        var count = 0

        for (date in dates) {
            for (leg in legs) {
                if (leg.op == false) continue // skip marketing legs
                if (storeFlight(airline, flightNumber, date, leg)) {
                    count++
                }
            }
        }
        return count
    }

    private fun expandPeriod(period: PeriodOfOperation): List<LocalDate> {
        val start = LocalDate.parse(period.startDate.uppercase(), ssimDateFormat)
        val end = LocalDate.parse(period.endDate.uppercase(), ssimDateFormat)
        val activeDays = parseDaysOfOperation(period.daysOfOperation)

        val dates = mutableListOf<LocalDate>()
        var current = start
        while (!current.isAfter(end)) {
            if (current.dayOfWeek in activeDays) {
                dates.add(current)
            }
            current = current.plusDays(1)
        }
        return dates
    }

    private fun parseDaysOfOperation(daysOfOp: String): Set<DayOfWeek> {
        val days = mutableSetOf<DayOfWeek>()
        for (i in daysOfOp.indices) {
            if (i < 7 && daysOfOp[i] != ' ') {
                days.add(DayOfWeek.of(i + 1))
            }
        }
        return days
    }

    private fun storeFlight(
        airline: String,
        flightNumber: Int,
        baseDate: LocalDate,
        leg: Leg,
    ): Boolean {
        val departureMinutes = leg.aircraftDepartureTimeUTC ?: return false
        val arrivalMinutes = leg.aircraftArrivalTimeUTC ?: return false
        val departureDateDiff = leg.aircraftDepartureTimeDateDiffUTC ?: 0
        val arrivalDateDiff = leg.aircraftArrivalTimeDateDiffUTC ?: 0

        val departureInstant = toInstant(baseDate, departureDateDiff, departureMinutes)
        val arrivalInstant = toInstant(baseDate, arrivalDateDiff, arrivalMinutes)
        val boardingInstant = departureInstant - properties.boardingOffsetMinutes.minutes

        val existing = FlightEntity.find {
            (FlightTable.airline eq airline) and
                (FlightTable.flightNumber eq flightNumber) and
                (FlightTable.departureTime eq departureInstant)
        }.firstOrNull()

        if (existing != null) return false

        FlightEntity.new {
            this.airline = airline
            this.flightNumber = flightNumber
            this.origin = leg.origin
            this.destination = leg.destination
            this.aircraftType = leg.aircraftType
            this.departureTime = departureInstant
            this.arrivalTime = arrivalInstant
            this.boardingTime = boardingInstant
            this.createdAt = Clock.System.now()
        }
        return true
    }

    private fun toInstant(baseDate: LocalDate, dateDiff: Int, minutesFromMidnight: Int): Instant {
        val date = baseDate.plusDays(dateDiff.toLong())
        val hours = minutesFromMidnight / 60
        val mins = minutesFromMidnight % 60
        val javaInstant = date.atTime(hours, mins).toInstant(java.time.ZoneOffset.UTC)
        return Instant.fromEpochSeconds(javaInstant.epochSecond)
    }
}
