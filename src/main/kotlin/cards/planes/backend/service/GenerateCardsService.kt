package cards.planes.backend.service

import cards.planes.backend.flight.FlightRepository
import cards.planes.backend.referencedata.ReferenceDataService
import cards.planes.generated.models.GameCard
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
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
            GameCard(
                seatNumber = "${Random.nextInt(1, 50)}${('A'..'F').random()}",
                flightNumber = flight.flightNumber.toString(),
                startAirport = referenceDataService.getAirport(flight.origin)?.name ?: "Unknown",
                boardingTime = OffsetDateTime.ofInstant(flight.boardingTime.toJavaInstant(), ZoneOffset.UTC),
                estimatedLanding = OffsetDateTime.ofInstant(flight.departureTime.toJavaInstant(), ZoneOffset.UTC),
                destinationAirport = referenceDataService.getAirport(flight.destination)?.name ?: "Unknown",
                aircraft = referenceDataService.getAircraft(flight.aircraftType)?.name ?: "Unknown",
                id = Uuid.random().toHexDashString(),
            )
        }
    }
}