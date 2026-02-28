package cards.planes.backend.controller

import cards.planes.generated.apis.PreGameApi
import cards.planes.generated.models.PartyJoin
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@RestController
class PreGameController : PreGameApi {
    companion object {
        private val logger = LoggerFactory.getLogger(PreGameController::class.java)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun joinGame(): ResponseEntity<PartyJoin> {
        logger.debug("join game requested")
        return ResponseEntity.ok().body(
            PartyJoin(
                yourId = Uuid.random().toHexDashString(),
                partyId = Uuid.random().toHexDashString(),
            )
        )
    }
}