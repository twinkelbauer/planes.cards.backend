package cards.planes.backend.referencedata

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.CollectionType
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

data class Aircraft(
    val code: String = "",
    val name: String = "",
    val airlineEquipCode: String? = null,
)

data class ReducedAirport(
    val iata: String = "",
    val name: String = "",
    val lat: Double? = null,
    val lan: Double? = null,
)

data class Airport(
    val code: String = "",
    val name: String = "",
    val cityCode: String? = null,
    val countryCode: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val timeZoneId: String? = null,
)

@Service
class ReferenceDataService {
    private val log = LoggerFactory.getLogger(javaClass)
    private val mapper = ObjectMapper().apply {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private lateinit var aircraftByCode: Map<String, Aircraft>
    private lateinit var airportByCode: Map<String, Airport>
    private lateinit var reducedAirportByCode: Map<String, ReducedAirport>

    @PostConstruct
    fun init() {
        val aircraftJson = ClassPathResource("lh-aircraft.json").inputStream.readBytes()
        val aircraftListType: CollectionType =
            mapper.typeFactory.constructCollectionType(List::class.java, Aircraft::class.java)
        val aircraftList: List<Aircraft> = mapper.readValue(aircraftJson, aircraftListType)
        aircraftByCode = aircraftList.associateBy { it.code }
        log.info("Loaded {} aircraft from lh-aircraft.json", aircraftByCode.size)

        val airportJson = ClassPathResource("lh-airports.json").inputStream.readBytes()
        val airportListType: CollectionType =
            mapper.typeFactory.constructCollectionType(List::class.java, Airport::class.java)
        val airportList: List<Airport> = mapper.readValue(airportJson, airportListType)
        airportByCode = airportList.associateBy { it.code }
        log.info("Loaded {} airports from lh-airports.json", airportByCode.size)


        val reducedAirportJson = ClassPathResource("airports.json").inputStream.readBytes()
        val reducedAirportListType: CollectionType =
            mapper.typeFactory.constructCollectionType(List::class.java, ReducedAirport::class.java)
        val reducedAirportList: List<ReducedAirport> = mapper.readValue(reducedAirportJson, reducedAirportListType)
        reducedAirportByCode = reducedAirportList.associateBy { it.iata }
        log.info("Loaded {} airports from lh-airports.json", reducedAirportByCode.size)
    }

    fun getAllAircraft(): List<Aircraft> = aircraftByCode.values.toList()
    fun getAircraft(code: String): Aircraft? = aircraftByCode[code]

    fun getAllAirports(): List<Airport> = airportByCode.values.toList()
    fun getAirport(code: String): Airport? = airportByCode[code]
    fun getReducedAirport(code: String): ReducedAirport? = reducedAirportByCode[code]
}
