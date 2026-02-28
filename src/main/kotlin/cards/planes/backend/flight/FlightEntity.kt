package cards.planes.backend.flight

import org.jetbrains.exposed.v1.core.dao.id.*
import org.jetbrains.exposed.v1.dao.*
import org.jetbrains.exposed.v1.jdbc.*
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class FlightEntity constructor(id: EntityID<Uuid>) : UuidEntity(id) {
    companion object : UuidEntityClass<FlightEntity>(FlightTable)

    var airline by FlightTable.airline
    var flightNumber by FlightTable.flightNumber
    var origin by FlightTable.origin
    var destination by FlightTable.destination
    var aircraftType by FlightTable.aircraftType
    var departureTime by FlightTable.departureTime
    var arrivalTime by FlightTable.arrivalTime
    var boardingTime by FlightTable.boardingTime
    var createdAt by FlightTable.createdAt
}
