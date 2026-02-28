package cards.planes.backend.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PublisherToFixBadCoding(
    private val gameStateService: GameStateService
) {

    @Scheduled(fixedRate = 5_000)
    fun pushPushPush() {
        gameStateService.publishAll()
    }
}