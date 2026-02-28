package cards.planes.backend.service

import cards.planes.backend.Calculator
import cards.planes.generated.models.*
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Service
class GameStateService(
    private val messagingTemplate: SimpMessagingTemplate,
    private val calculator: Calculator,
) {
    companion object {
        private val logger = LoggerFactory.getLogger(GameStateService::class.java)
        private val AIRPORTS = listOf(
            "HAMBURG",
            "BERLIN",
            "MUNICH",
            "FRANKFURT",
            "COLOGNE",
            "WASHINGTON DC",
            "NEW YORK",
            "LOS ANGELES",
            "LONDON",
            "PARIS"
        )
        private val AIRCRAFT_TYPES = listOf(
            "BOEING 747 MEGA LARGE",
            "AIRBUS A380 GIGANTIC",
            "BOEING 777 SUPER",
            "AIRBUS A330 WIDE",
            "BOEING 787 DREAM"
        )
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

        val initialCards = generateRandomCards(7)
        val newPlayer = GamePlayer(
            id = playerId,
            cards = initialCards,
            score = 0,
            playedCard = null
        )

        val updatedPlayers = party.players + newPlayer
        val updatedParty = party.copy(players = updatedPlayers)

        parties[partyId] = updatedParty
        playerParties[playerId] = partyId

        // If party is full, mark it as no longer waiting
        if (updatedPlayers.size >= maxPlayersPerParty) {
            logger.info(
                "Party {} is full with {} players, no longer accepting new players",
                partyId,
                updatedPlayers.size
            )
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

        logger.info("Created new waiting party: {}", newPartyId)
        return newPartyId
    }

    fun getPartyState(partyId: String): PartyGame? {
        return parties[partyId]
    }

    fun updateParty(partyId: String, playerId: String, update: PartyClientUpdate): Boolean {
        val party = parties[partyId] ?: return false

        logger.info("Player {} updating party {} with: {}", playerId, partyId, update)

        if (update.playCard != null) {
            // Remove the played card from player's hand immediately
            val updatedPlayers = party.players.map { player ->
                if (player.id == playerId) {
                    player.copy(
                        playedCard = update.playCard,
                        cards = player.cards.filter { it != update.playCard }
                    )
                } else player
            }

            val allPlayersPlayed = updatedPlayers.all { it.playedCard != null }

            if (allPlayersPlayed) {
                // BATTLE TIME! 🔥 Determine round winner and update scores
                val winner = determineRoundWinner(updatedPlayers)
                val finalPlayers = updatedPlayers.map { player ->
                    player.copy(
                        score = if (player.id == winner.id) player.score + 1 else player.score,
                        playedCard = null // Clear played cards immediately after battle
                    )
                }

                val gameEnded = finalPlayers.any { it.cards.isEmpty() }
                val newState = if (gameEnded) GameState.ENDING else GameState.WAITING

                val updatedParty = party.copy(
                    players = finalPlayers,
                    state = newState,
                    lastRoundWinner = winner.id
                )

                parties[partyId] = updatedParty
                messagingTemplate.convertAndSend("/topic/party/$partyId", updatedParty)

                logger.info("Round complete! Winner: {} (Score: {})", winner.id.substring(0, 8), winner.score + 1)
            } else {
                // Just update with played card, waiting for other players
                val updatedParty = party.copy(players = updatedPlayers)
                parties[partyId] = updatedParty
                messagingTemplate.convertAndSend("/topic/party/$partyId", updatedParty)
            }

            return true
        }

        return false
    }

    private fun determineRoundWinner(players: List<GamePlayer>): GamePlayer {
        logger.info("🚀 BATTLE TIME! Comparing cards...")

        // Calculate card power for each player
        val playerPowers = players.map { player ->
            val card = player.playedCard!!
            val power = calculator(card)
            logger.info(
                "Player {}: {} = {} power",
                player.id.substring(0, 8), card.flightNumber, power
            )
            player to power
        }

        // Find the winner (highest power)
        val winner = playerPowers.maxByOrNull { it.second }!!.first
        logger.info(
            "🏆 WINNER: Player {} with {} power!",
            winner.id.substring(0, 8), playerPowers.find { it.first == winner }!!.second
        )

        return winner
    }

    private fun generateRandomCards(count: Int): List<GameCard> {
        return (1..count).map {
            val start = AIRPORTS.random()
            val destination = AIRPORTS.filter { it != start }.random()
            val boardingTime = OffsetDateTime.now(ZoneOffset.UTC).plusHours(Random.nextLong(1, 24))
            val landing = boardingTime.plusHours(Random.nextLong(1, 12))

            GameCard(
                aircraft = AIRCRAFT_TYPES.random(),
                seatNumber = "${Random.nextInt(1, 50)}${('A'..'F').random()}",
                flightNumber = "${('A'..'Z').random()}${('A'..'Z').random()}${Random.nextInt(100, 999)}",
                startAirport = start,
                destinationAirport = destination,
                boardingTime = boardingTime,
                estimatedLanding = landing
            )
        }
    }

    fun getPlayerParty(playerId: String): String? {
        return playerParties[playerId]
    }
}