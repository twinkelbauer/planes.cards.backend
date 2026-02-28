package cards.planes.backend.lufthansa

import cards.planes.backend.config.LufthansaProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import java.time.Instant

@Service
class LufthansaTokenService(
    private val properties: LufthansaProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val restClient = RestClient.create()

    @Volatile
    private var cachedToken: String? = null

    @Volatile
    private var tokenExpiresAt: Instant = Instant.EPOCH

    @Synchronized
    fun getAccessToken(): String {
        if (cachedToken != null && Instant.now().isBefore(tokenExpiresAt)) {
            return cachedToken!!
        }
        return refreshToken()
    }

    private fun refreshToken(): String {
        log.info("Requesting new Lufthansa API access token")
        val response = restClient.post()
            .uri("${properties.baseUrl}/v1/oauth/token")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .body(
                "client_id=${properties.clientId}" +
                    "&client_secret=${properties.clientSecret}" +
                    "&grant_type=client_credentials"
            )
            .retrieve()
            .body(TokenResponse::class.java)
            ?: throw RuntimeException("Failed to obtain access token from Lufthansa API")

        cachedToken = response.accessToken
        // Refresh 60 seconds before actual expiry
        tokenExpiresAt = Instant.now().plusSeconds(response.expiresIn - 60)
        log.info("Obtained Lufthansa API token, expires in {}s", response.expiresIn)
        return response.accessToken
    }

    data class TokenResponse(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("token_type") val tokenType: String,
        @JsonProperty("expires_in") val expiresIn: Long,
    )
}
