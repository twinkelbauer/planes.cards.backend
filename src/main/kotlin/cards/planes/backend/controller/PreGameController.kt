package cards.planes.backend.controller

import cards.planes.backend.service.GameStateService
import cards.planes.generated.apis.PreGameApi
import cards.planes.generated.models.PartyJoin
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PreGameController(
    private val gameStateService: GameStateService
) : PreGameApi {
    companion object {
        private val logger = LoggerFactory.getLogger(PreGameController::class.java)
    }

    override fun joinGame(): ResponseEntity<PartyJoin> {
        logger.debug("join game requested")
        val partyJoin = gameStateService.joinParty()
        return ResponseEntity.ok().body(partyJoin)
    }
}