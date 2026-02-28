package cards.planes.backend.flight

import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import kotlin.time.ExperimentalTime

@Repository
class FlightRepository(
) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @OptIn(ExperimentalTime::class)
    fun getRandomFlights(count: Int): List<FlightDto> {
        return transaction {
            val ids = FlightTable.select(FlightTable.flightNumber)
                .distinct()
                .map { flight ->
                    flight[FlightTable.flightNumber]
                }
                .toSet()

            val mutableIds = mutableSetOf<Int>()
            while (mutableIds.size < count) {
                runCatching { mutableIds.add(ids.random()) }
            }

            FlightTable.selectAll().where { FlightTable.flightNumber inList mutableIds }
                .distinct()
                .map { flight ->
                    FlightDto(
                        airline = flight[FlightTable.airline],
                        flightNumber = flight[FlightTable.flightNumber],
                        origin = flight[FlightTable.origin],
                        destination = flight[FlightTable.destination],
                        aircraftType = flight[FlightTable.aircraftType],
                        departureTime = flight[FlightTable.departureTime],
                        arrivalTime = flight[FlightTable.arrivalTime],
                        boardingTime = flight[FlightTable.boardingTime],
                    )
                }
                .take(count)
        }
    }
}