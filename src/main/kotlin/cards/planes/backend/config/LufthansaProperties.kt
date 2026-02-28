package cards.planes.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "lufthansa")
data class LufthansaProperties(
    val clientId: String = "",
    val clientSecret: String = "",
    val baseUrl: String = "https://api.lufthansa.com",
    val airports: List<String> = listOf("FRA", "MUC", "HAM", "BER"),
    val airlines: List<String> = listOf("LH"),
    val fetchDaysAhead: Int = 7,
    val boardingOffsetMinutes: Long = 40,
    val schedule: ScheduleProperties = ScheduleProperties(),
) {
    data class ScheduleProperties(
        val fetchCron: String = "0 0 */4 * * *",
        val cleanupCron: String = "0 0 1 * * *",
    )
}
