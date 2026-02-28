package cards.planes.backend.service

import cards.planes.generated.models.GameCard
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.random.Random

@Service
class GenerateCardsService {
    companion object {
        private val logger = LoggerFactory.getLogger(GenerateCardsService::class.java)

        private val AIRPORTS = listOf(
            "HAMBURG",
            "BERLIN",
            "MUNICH",
            "FRANKFURT",
            "COLOGNE",
            "WASHINGTON DC",
            "NEW YORK",
            "LOS ANGELES",
            "LONDON",
            "PARIS"
        )
        private val AIRCRAFT_TYPES = listOf(
            "BOEING 747 MEGA LARGE",
            "AIRBUS A380 GIGANTIC",
            "BOEING 777 SUPER",
            "AIRBUS A330 WIDE",
            "BOEING 787 DREAM"
        )
    }

    fun generateCards(): List<GameCard> {
        logger.info("Generating cards")
        return generateRandomCards(7)
    }

    private fun generateRandomCards(count: Int): List<GameCard> {
        return (1..count).map {
            val start = AIRPORTS.random()
            val destination = AIRPORTS.filter { it != start }.random()
            val boardingTime = OffsetDateTime.now(ZoneOffset.UTC).plusHours(Random.nextLong(1, 24))
            val landing = boardingTime.plusHours(Random.nextLong(1, 12))

            GameCard(
                aircraft = AIRCRAFT_TYPES.random(),
                seatNumber = "${Random.nextInt(1, 50)}${('A'..'F').random()}",
                flightNumber = "${('A'..'Z').random()}${('A'..'Z').random()}${Random.nextInt(100, 999)}",
                startAirport = start,
                destinationAirport = destination,
                boardingTime = boardingTime,
                estimatedLanding = landing
            )
        }
    }
}