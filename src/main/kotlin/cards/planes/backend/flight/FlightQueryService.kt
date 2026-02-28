package cards.planes.backend.flight

import org.jetbrains.exposed.v1.core.dao.id.*
import org.jetbrains.exposed.v1.core.*
import org.jetbrains.exposed.v1.dao.*
import org.jetbrains.exposed.v1.jdbc.*
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Service
class FlightQueryService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun getUpcomingFlights(origin: String, limit: Int = 20): List<FlightDto> = transaction {
        val now = Clock.System.now()
        FlightEntity.find {
            (FlightTable.origin eq origin) and (FlightTable.departureTime greater now)
        }
            .orderBy(FlightTable.departureTime to SortOrder.ASC)
            .limit(limit)
            .map { it.toDto() }
    }

    fun getUpcomingFlights(origins: List<String>, limit: Int = 20): List<FlightDto> = transaction {
        val now = Clock.System.now()
        FlightEntity.find {
            (FlightTable.origin inList origins) and (FlightTable.departureTime greater now)
        }
            .orderBy(FlightTable.departureTime to SortOrder.ASC)
            .limit(limit)
            .map { it.toDto() }
    }

    fun deleteExpiredFlights(): Int = transaction {
        val now = Clock.System.now()
        val count = FlightTable.deleteWhere { departureTime less now }
        log.info("Cleaned up {} expired flights", count)
        count
    }
}
