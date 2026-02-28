package cards.planes.backend.service

import cards.planes.backend.config.MetricsConfig
import cards.planes.generated.models.*
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class GameStateService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val generateCardsService: GenerateCardsService,
    private val winnerSelectService: WinnerSelectService,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(GameStateService::class.java)
    }

    private val parties = ConcurrentHashMap<String, PartyGame>()
    private val playerParties = ConcurrentHashMap<String, String>()
    private var currentWaitingParty: String? = null
    private val maxPlayersPerParty = 2

    @OptIn(ExperimentalUuidApi::class)
    @Synchronized
    fun joinParty(): PartyJoin {
        val playerId = Uuid.random().toHexDashString()

        // Find or create a waiting party
        val partyId = findOrCreateWaitingParty()
        val party = parties[partyId]!!

        logger.info("Player {} joining party {} (current players: {})", playerId, partyId, party.players.size)

        val newPlayer = GamePlayer(
            id = playerId,
            cards = emptyList(),
            score = 0,
            playedCard = null,
            yourTurn = false,
        )

        val updatedPlayers = party.players + newPlayer
        var updatedParty = party.copy(players = updatedPlayers)

        parties[partyId] = updatedParty
        playerParties[playerId] = partyId

        // If party is full, mark it as no longer waiting
        if (updatedPlayers.size >= maxPlayersPerParty) {
            logger.info(
                "Party {} is full with {} players, no longer accepting new players",
                partyId,
                updatedPlayers.size
            )

            updatedParty = updatedParty.copy(
                players = updatedParty.players.mapIndexed { index, player ->
                    player.copy(
                        cards = generateCardsService.generateCards(),
                        yourTurn = index == 0,
                    )
                },
                state = GameState.WAITING_FOR_CARDS,
            )
            parties[partyId] = updatedParty

            currentWaitingParty = null
        }

        // Broadcast the updated party state to existing players
        messagingTemplate.convertAndSend("/topic/party/$partyId", updatedParty)

        return PartyJoin(yourId = playerId, partyId = partyId)
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun findOrCreateWaitingParty(): String {
        // Check if current waiting party exists and has space
        currentWaitingParty?.let { partyId ->
            val party = parties[partyId]
            if (party != null && party.players.size < maxPlayersPerParty) {
                return partyId
            }
        }

        // Create new party
        val newPartyId = Uuid.random().toHexDashString()
        val newParty = PartyGame(
            partyId = newPartyId,
            state = GameState.WAITING,
            players = emptyList(),
            lastRoundWinner = null
        )

        parties[newPartyId] = newParty
        currentWaitingParty = newPartyId

        MetricsConfig.partyCounter.increment()
        logger.info("Created new waiting party: {}", newPartyId)
        return newPartyId
    }

    fun getPartyState(partyId: String): PartyGame? {
        return parties[partyId].also {
            if (it != null) {
                messagingTemplate.convertAndSend("/topic/party/$partyId", it)
            }
        }
    }

    fun updateParty(partyId: String, playerId: String, update: PartyClientUpdate): Boolean {
        val party = parties[partyId] ?: return false

        logger.info("Player {} updating party {} with: {}", playerId, partyId, update)

        if (update.playCard != null) {
            val player = requireNotNull(party.players.find { it.id == update.yourId })

            // check if player is allowed to play
            if (player.playedCard != null && !player.yourTurn) {
                logger.info("Player {} already played {}", player.id, player.playedCard.flightNumber)
                return false
            }

            // Remove the played card from player's hand immediately
            var updatedPlayers = party.players.map { player ->
                if (player.id == playerId) {
                    player.copy(
                        playedCard = update.playCard.playedCard,
                        cards = player.cards.filter { it != update.playCard },
                        yourTurn = false
                    )
                } else {
                    player.copy(
                        yourTurn = true,
                    )
                }
            }

            val allPlayersPlayed = updatedPlayers.all { it.playedCard != null }


            if (allPlayersPlayed) {
                // BATTLE TIME! 🔥 Determine round winner and update scores
                val (leftPlayer, rightPlayer) = updatedPlayers.sortedBy { it.yourTurn }
                val winner = winnerSelectService.selectWinner(update.playCard.attribute, leftPlayer, rightPlayer)
                updatedPlayers = updatedPlayers.map { player ->
                    player.copy(
                        score = if (winner == null || player.id == winner.id) {
                            player.score + 1
                        } else {
                            player.score
                        },
                        playedCard = null,// Clear played cards immediately after battle
                        cards = player.cards.filterNot { it == requireNotNull(player.playedCard) }
                    )
                }

                // Toggle when you weren't first the last time
                updatedPlayers = updatedPlayers.map { player -> player.copy(yourTurn = !player.yourTurn) }

                val gameEnded = updatedPlayers.any { it.cards.isEmpty() }
                val newState = if (gameEnded) GameState.ENDING else GameState.WAITING_FOR_CARDS

                val updatedParty = party.copy(
                    players = updatedPlayers,
                    state = newState,
                    lastRoundWinner = winner?.id
                )

                parties[partyId] = updatedParty
                messagingTemplate.convertAndSend("/topic/party/$partyId", updatedParty)

                logger.info(
                    "Round complete! Winner: {} (Score: {})", winner?.id?.substring(0, 8),
                    (winner?.score ?: 0) + 1
                )
            } else {
                logger.info("Player with id {} is empty", player.id)
                // Just update with played card, waiting for other players
                val updatedParty = party.copy(players = updatedPlayers)
                parties[partyId] = updatedParty
                messagingTemplate.convertAndSend("/topic/party/$partyId", updatedParty)
            }

            return true
        }

        return false
    }
}