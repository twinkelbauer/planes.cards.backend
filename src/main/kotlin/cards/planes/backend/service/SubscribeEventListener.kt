package cards.planes.backend.service

import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent


@Component
class SubscribeEventListener(
    private val gameStateService: GameStateService
) {

    @EventListener
    fun handleSubscribe(event: SessionSubscribeEvent) {
        val headers = StompHeaderAccessor.wrap(event.message)
        val destination = headers.destination

        if (requireNotNull(destination?.contains("/topic/party"))) {
            val partyId = requireNotNull(headers.destination?.split("/")?.last())
            gameStateService.getPartyState(partyId)
        }
    }
}