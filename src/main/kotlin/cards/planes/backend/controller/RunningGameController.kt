package cards.planes.backend.controller

import cards.planes.backend.service.GameStateService
import cards.planes.generated.apis.RunningGameApi
import cards.planes.generated.models.GameState
import cards.planes.generated.models.PartyClientUpdate
import cards.planes.generated.models.PartyGame
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RunningGameController(
    private val gameStateService: GameStateService
) : RunningGameApi {
    companion object {
        private val logger = LoggerFactory.getLogger(RunningGameController::class.java)
    }

    @SubscribeMapping("/topic/party/{partyId}")
    fun subscribeToParty(@DestinationVariable partyId: String): PartyGame? {
        logger.info("Client subscribing to party: {}", partyId)
        return gameStateService.getPartyState(partyId)
    }

    @MessageMapping("/party/{partyId}/update")
    @SendTo("/topic/party/{partyId}")
    fun handlePartyUpdate(
        @DestinationVariable partyId: String,
        update: PartyClientUpdate
    ): Any? {
        logger.info("Received update for party {}: {}", partyId, update)

        val success = gameStateService.updateParty(partyId, update.yourId, update)

        return if (success) {
            gameStateService.getPartyState(partyId)
        } else {
            logger.warn("Failed to update party {}", partyId)
            null
        }
    }

    override fun getPartyUpdates(partyId: String): ResponseEntity<Unit> {
        logger.info("WebSocket connection requested for party: {}", partyId)
        return ResponseEntity.ok().build()
    }

    override fun sendPartyUpdates(partyId: String, partyClientUpdate: PartyClientUpdate?): ResponseEntity<Unit> {
        logger.info("REST update for party {}: {}", partyId, partyClientUpdate)

        val success = if (partyClientUpdate != null) {
            gameStateService.updateParty(partyId, partyClientUpdate.yourId, partyClientUpdate)
        } else false

        return if (success) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.badRequest().build()
        }
    }
}