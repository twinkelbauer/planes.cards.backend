package cards.planes.backend.lufthansa

import cards.planes.backend.config.LufthansaProperties
import org.openapitools.client.ApiClient
import org.openapitools.client.api.ScheduleApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class LufthansaApiClientConfig {

    @Bean
    fun scheduleApi(
        properties: LufthansaProperties,
        tokenService: LufthansaTokenService,
    ): ScheduleApi {
        val authFilter = ExchangeFilterFunction.ofRequestProcessor { request ->
            val token = tokenService.getAccessToken()
            Mono.just(
                org.springframework.web.reactive.function.client.ClientRequest.from(request)
                    .header("Authorization", "Bearer $token")
                    .build()
            )
        }

        val webClient = WebClient.builder()
            .baseUrl("${properties.baseUrl}/v1/flight-schedules")
            .filter(authFilter)
            .build()

        val apiClient = ApiClient(webClient)
        apiClient.basePath = "${properties.baseUrl}/v1/flight-schedules"
        return ScheduleApi(apiClient)
    }
}
