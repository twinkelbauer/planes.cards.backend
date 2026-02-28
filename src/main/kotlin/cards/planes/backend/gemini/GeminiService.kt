package cards.planes.backend.gemini

import cards.planes.backend.config.GeminiProperties
import cards.planes.backend.flight.FlightDto
import cards.planes.backend.referencedata.ReferenceDataService
import com.google.auth.oauth2.GoogleCredentials
import com.google.genai.Client
import com.google.genai.types.Content
import com.google.genai.types.GenerateContentConfig
import com.google.genai.types.GoogleSearch
import com.google.genai.types.Part
import com.google.genai.types.Tool
import java.io.FileInputStream
import kotlin.time.ExperimentalTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@OptIn(ExperimentalTime::class)
@Service
class GeminiService(
    private val geminiProperties: GeminiProperties,
    private val referenceDataService: ReferenceDataService,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val client: Client by lazy {
        val credentials = GoogleCredentials
            .fromStream(FileInputStream(geminiProperties.serviceAccountKeyPath))
            .createScoped("https://www.googleapis.com/auth/cloud-platform")
        Client.builder()
            .vertexAI(true)
            .project(geminiProperties.project)
            .location(geminiProperties.location)
            .credentials(credentials)
            .build()
    }

    fun askAboutFlight(flight: FlightDto, question: String): String {
        val originName = referenceDataService.getAirport(flight.origin)?.name ?: flight.origin
        val destinationName = referenceDataService.getAirport(flight.destination)?.name ?: flight.destination
        val aircraftName = referenceDataService.getAircraft(flight.aircraftType)?.name ?: flight.aircraftType

        val prompt = """
            |Flight: ${flight.airline}${flight.flightNumber}
            |Route: $originName (${flight.origin}) -> $destinationName (${flight.destination})
            |Aircraft: $aircraftName (${flight.aircraftType})
            |Departure: ${flight.departureTime}
            |Arrival: ${flight.arrivalTime}
            |
            |Question: $question
        """.trimMargin()

        log.info("Asking Gemini about flight {}{}: {}", flight.airline, flight.flightNumber, question)

        val googleSearchTool = Tool.builder()
            .googleSearch(GoogleSearch.builder().build())
            .build()

        val config = GenerateContentConfig.builder()
            .tools(listOf(googleSearchTool))
            .systemInstruction(Content.fromParts(Part.fromText(SYSTEM_INSTRUCTION)))
            .build()

        val response = client.models.generateContent(
            geminiProperties.model,
            prompt,
            config,
        )

        val text = response.text() ?: "I couldn't generate a response for this flight."
        log.info("Gemini response length: {} chars", text.length)
        return text
    }

    companion object {
        private const val SYSTEM_INSTRUCTION =
            "You are a friendly aviation expert assistant for a flight information app. " +
            "You answer questions about flights, routes, airports, and aircraft. " +
            "Keep your answers concise (2-4 sentences), interesting, and factual. " +
            "Use Google Search to ground your answers in real-world facts when possible. " +
            "If asked for a fun fact, provide something genuinely interesting about the " +
            "route, the cities, the aircraft, or aviation history related to the flight."
    }
}
