package cards.planes.backend.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "gemini")
data class GeminiProperties(
    val serviceAccountKeyPath: String = "",
    val project: String = "",
    val location: String = "us-central1",
    val model: String = "gemini-2.5-flash",
)
