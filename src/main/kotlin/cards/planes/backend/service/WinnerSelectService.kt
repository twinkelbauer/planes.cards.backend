package cards.planes.backend.service

import cards.planes.generated.models.GamePlayer
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.reflect.full.memberProperties

@Service
class WinnerSelectService {
    companion object {
        private val logger = LoggerFactory.getLogger(WinnerSelectService::class.java)
    }

    /**
     * When null it's a tie
     */
    fun selectWinner(
        attribute: String,
        leftPlayer: GamePlayer,
        rightPlayer: GamePlayer,
    ): GamePlayer? {
        val leftCard = requireNotNull(leftPlayer.playedCard)
        val rightCard = requireNotNull(rightPlayer.playedCard)

        return when (compareAttribute(leftCard, rightCard, attribute)) {
            -1 -> leftPlayer
            1 -> rightPlayer
            else -> null
        }.also {
            logger.info("Compare {} to {} with attribute {}, winner was {}", leftCard, rightCard, attribute, it?.id)
        }
    }

    private fun compareAttribute(left: Any, right: Any, attribute: String): Int {
        val prop = left::class.memberProperties.find { it.name == attribute }

        val leftVal = prop?.getter?.call(left)
        val rightVal = prop?.getter?.call(right)

        if (leftVal is Comparable<*> && rightVal is Comparable<*>) {
            @Suppress("UNCHECKED_CAST")
            return (leftVal as Comparable<Any>).compareTo(rightVal)
        }

        throw IllegalArgumentException("Property '$attribute' is not comparable")
    }
}