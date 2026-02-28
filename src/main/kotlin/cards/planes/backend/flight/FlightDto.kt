package cards.planes.backend.flight

import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class FlightDto(
    val airline: String,
    val flightNumber: Int,
    val origin: String,
    val destination: String,
    val aircraftType: String,
    val departureTime: Instant,
    val arrivalTime: Instant,
    val boardingTime: Instant,
)

@OptIn(ExperimentalTime::class)
fun FlightEntity.toDto() = FlightDto(
    airline = airline,
    flightNumber = flightNumber,
    origin = origin,
    destination = destination,
    aircraftType = aircraftType,
    departureTime = departureTime,
    arrivalTime = arrivalTime,
    boardingTime = boardingTime,
)
