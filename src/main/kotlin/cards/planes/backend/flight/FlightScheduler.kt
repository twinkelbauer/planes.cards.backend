package cards.planes.backend.flight

import cards.planes.backend.config.LufthansaProperties
import org.openapitools.client.api.ScheduleApi
import org.openapitools.client.model.TimeMode
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Component
class FlightScheduler(
    private val scheduleApi: ScheduleApi,
    private val ingestionService: FlightIngestionService,
    private val queryService: FlightQueryService,
    private val properties: LufthansaProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val ssimDateFormat = DateTimeFormatter.ofPattern("ddMMMyy", Locale.ENGLISH)
    private val allDays = "1234567"

    @EventListener(ApplicationReadyEvent::class)
    fun onStartup() {
        if (!queryService.hasFlights()) {
            log.info("No flights in database, performing initial fetch")
            fetchFlights()
        }
    }

    @Scheduled(cron = "\${lufthansa.schedule.fetch-cron}")
    fun fetchFlights() {
        log.info("Starting scheduled flight fetch for airports: {}", properties.airports)
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(properties.fetchDaysAhead.toLong())
        val startStr = startDate.format(ssimDateFormat).uppercase()
        val endStr = endDate.format(ssimDateFormat).uppercase()

        for (airport in properties.airports) {
            try {
                val aggregates = scheduleApi.flightschedulesPassengerGet(
                    properties.airlines,
                    startStr,
                    endStr,
                    allDays,
                    TimeMode.UTC,
                    null, // flightNumberRanges
                    airport,
                    null, // destination
                    null, // aircraftTypes
                ).collectList().block() ?: emptyList()

                val count = ingestionService.ingest(aggregates)
                log.info("Ingested {} new flights for airport {}", count, airport)
            } catch (e: Exception) {
                log.error(e.stackTraceToString())
                log.error("Failed to fetch flights for airport {}: {}", airport, e.message)
            }
        }
    }

    @Scheduled(cron = "\${lufthansa.schedule.cleanup-cron}")
    fun cleanupExpiredFlights() {
        queryService.deleteExpiredFlights()
    }
}
