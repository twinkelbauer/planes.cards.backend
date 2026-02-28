package cards.planes.backend.service

import cards.planes.backend.flight.FlightRepository
import cards.planes.backend.referencedata.ReferenceDataService
import cards.planes.generated.models.GameCard
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.random.Random
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class GenerateCardsService(
    private val flightRepository: FlightRepository,
    private val referenceDataService: ReferenceDataService,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(GenerateCardsService::class.java)
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    fun generateCards(): List<GameCard> {
        val flights = flightRepository.getRandomFlights(6)

        logger.info("Found ${flights.size} flight cards")

        return flights.map { flight ->
            val boarding = OffsetDateTime.ofInstant(flight.boardingTime.toJavaInstant(), ZoneOffset.UTC)
            val estimated = OffsetDateTime.ofInstant(flight.departureTime.toJavaInstant(), ZoneOffset.UTC)
            val airport = requireNotNull(referenceDataService.getReducedAirport(flight.origin))
            val destAirport = requireNotNull(referenceDataService.getReducedAirport(flight.destination))
            val flightTime = Duration.between(boarding, estimated).toMinutes()
            GameCard(
                seatNumber = "${Random.nextInt(1, 50)}${('A'..'F').random()}",
                flightNumber = flight.flightNumber.toString(),
                startAirport = airport.name,
                boardingTime = boarding,
                estimatedLanding = estimated,
                destinationAirport = airport.name,
                aircraft = referenceDataService.getAircraft(flight.aircraftType)?.name ?: "Unknown",
                id = Uuid.random().toHexDashString(),
                flightTime = BigDecimal.valueOf(flightTime),
                travelDistance = runCatching {
                    BigDecimal.valueOf(
                        roughDistance(
                            lat1 = airport.lat!!,
                            lon1 = airport.lan!!,
                            lat2 = destAirport.lat!!,
                            lon2 = destAirport.lan!!,
                        )
                    )
                }.getOrElse { BigDecimal.valueOf(Random.nextLong()) }
            )
        }
    }

    fun roughDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371000.0 // meters

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2)

        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c
    }
}