package cards.planes.backend.config

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.web.exchanges.HttpExchange
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Configuration
class MetricsConfig {

    private val logger = LoggerFactory.getLogger(MetricsConfig::class.java)

    @Bean
    fun httpExchangeRepository(): HttpExchangeRepository {
        return InMemoryHttpExchangeRepository()
    }

    @Bean
    fun requestLoggingFilter(meterRegistry: MeterRegistry): OncePerRequestFilter {
        return object : OncePerRequestFilter() {
            override fun doFilterInternal(
                request: HttpServletRequest,
                response: HttpServletResponse,
                filterChain: FilterChain
            ) {
                val startTime = System.currentTimeMillis()
                val timer = Timer.start(meterRegistry)

                try {
                    filterChain.doFilter(request, response)
                } finally {
                    val duration = System.currentTimeMillis() - startTime
                    timer.stop(
                        Timer.builder("http.server.requests.custom")
                            .tag("method", request.method)
                            .tag("uri", request.requestURI)
                            .tag("status", response.status.toString())
                            .register(meterRegistry)
                    )

                    logger.info(
                        "HTTP Request: ${request.method} ${request.requestURI} - Status: ${response.status} - Duration: ${duration}ms - User-Agent: ${request.getHeader("User-Agent") ?: "Unknown"} - Remote IP: ${getClientIpAddress(request)}"
                    )
                }
            }

            private fun getClientIpAddress(request: HttpServletRequest): String {
                val xForwardedFor = request.getHeader("X-Forwarded-For")
                if (!xForwardedFor.isNullOrEmpty() && !"unknown".equals(xForwardedFor, true)) {
                    return xForwardedFor.split(",")[0].trim()
                }

                val xRealIp = request.getHeader("X-Real-IP")
                if (!xRealIp.isNullOrEmpty() && !"unknown".equals(xRealIp, true)) {
                    return xRealIp
                }

                return request.remoteAddr ?: "unknown"
            }
        }
    }
}