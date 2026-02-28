package cards.planes.backend.flight

import org.jetbrains.exposed.v1.core.dao.id.UuidTable
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
object FlightTable : UuidTable("flights") {
    val airline = varchar("airline", 3)
    val flightNumber = integer("flight_number")
    val origin = varchar("origin", 3)
    val destination = varchar("destination", 3)
    val aircraftType = varchar("aircraft_type", 3)
    val departureTime = timestamp("departure_time")
    val arrivalTime = timestamp("arrival_time")
    val boardingTime = timestamp("boarding_time")
    val createdAt = timestamp("created_at")

    init {
        uniqueIndex("uq_flight", airline, flightNumber, departureTime)
        index("idx_origin_departure", false, origin, departureTime)
    }
}
