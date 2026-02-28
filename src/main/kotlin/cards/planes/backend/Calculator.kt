package cards.planes.backend

import cards.planes.generated.models.GameCard
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class Calculator {
    companion object {
        private val logger = LoggerFactory.getLogger(Calculator::class.java)
    }

    operator fun invoke(card: GameCard): Int {
        var power = 0

        // Extract numbers from flight number (e.g., "AA123" -> 123)
        val flightNumberStr = buildString {
            card.flightNumber.forEach { if (it.isDigit()) append(it) }
        }
        power += flightNumberStr.toIntOrNull() ?: 0

        // Extract numbers from seat number (e.g., "12A" -> 12)
        val seatNumberStr = buildString {
            card.seatNumber.forEach { if (it.isDigit()) append(it) }
        }
        power += seatNumberStr.toIntOrNull() ?: 0

        // Add boarding time hour and minute
        try {
            val boardingTime = card.boardingTime
            power += boardingTime.hour + boardingTime.minute
        } catch (e: Exception) {
            logger.debug("Could not parse boarding time for power calculation")
        }

        // Add landing time hour and minute
        try {
            val landingTime = card.estimatedLanding
            power += landingTime.hour + landingTime.minute
        } catch (e: Exception) {
            logger.debug("Could not parse landing time for power calculation")
        }

        // Bonus points for aircraft type length (bigger plane = more power!)
        power += (card.aircraft?.length ?: 0)

        // Bonus for airport name lengths (prestigious airports!)
        power += card.startAirport.length + card.destinationAirport.length

        return power
    }
}