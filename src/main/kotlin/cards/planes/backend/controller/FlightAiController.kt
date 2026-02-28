package cards.planes.backend.controller

import cards.planes.backend.flight.FlightQueryService
import cards.planes.backend.gemini.GeminiService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/flights")
class FlightAiController(
    private val flightQueryService: FlightQueryService,
    private val geminiService: GeminiService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/ask")
    fun askAboutFlight(
        @RequestParam airline: String,
        @RequestParam flightNumber: Int,
        @RequestParam question: String,
    ): ResponseEntity<FlightAiResponse> {
        log.info("AI question for {}{}: {}", airline, flightNumber, question)

        val flight = flightQueryService.getFlightByNumber(airline, flightNumber)
            ?: return ResponseEntity.notFound().build()

        val answer = geminiService.askAboutFlight(flight, question)

        return ResponseEntity.ok(
            FlightAiResponse(
                flightNumber = "${flight.airline}${flight.flightNumber}",
                question = question,
                answer = answer,
            )
        )
    }

    @GetMapping("/fun-fact")
    fun funFact(
        @RequestParam airline: String,
        @RequestParam flightNumber: Int,
    ): ResponseEntity<FlightAiResponse> {
        log.info("Fun fact requested for {}{}", airline, flightNumber)

        val flight = flightQueryService.getFlightByNumber(airline, flightNumber)
            ?: return ResponseEntity.notFound().build()

        val answer = geminiService.askAboutFlight(flight, "Tell me a fun fact about this flight route. Make it at MOST one sentence. And really funny. My life depends on it.")

        return ResponseEntity.ok(
            FlightAiResponse(
                flightNumber = "${flight.airline}${flight.flightNumber}",
                question = "internal: fun fact",
                answer = answer,
            )
        )
    }
}

data class FlightAiResponse(
    val flightNumber: String,
    val question: String,
    val answer: String,
)
