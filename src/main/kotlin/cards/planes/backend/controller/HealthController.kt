package cards.planes.backend.controller

import cards.planes.generated.apis.HealthApi
import cards.planes.generated.models.Health200Response
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class HealthController : HealthApi {
    companion object {
        private val log = LoggerFactory.getLogger(HealthController::class.java)
    }

    override fun health(): ResponseEntity<Health200Response> {
        log.info("Health API called")
        return ResponseEntity.ok().body(Health200Response("OK"))
    }
}