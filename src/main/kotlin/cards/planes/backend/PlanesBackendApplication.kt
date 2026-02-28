package cards.planes.backend

import cards.planes.backend.flight.FlightTable
import jakarta.annotation.PostConstruct
import org.jetbrains.exposed.v1.core.StdOutSqlLogger
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
class PlanesBackendApplication

fun main(args: Array<String>) {
    runApplication<PlanesBackendApplication>(*args)
}

@Component
class PostStartup {

    @Value($$"${spring.datasource.url}")
    lateinit var url: String

    @Value($$"${spring.datasource.username}")
    lateinit var username: String

    @Value($$"${spring.datasource.password}")
    lateinit var password: String

    @PostConstruct
    fun init() {
        Database.connect(url, driver = "org.postgresql.Driver", user = username, password = password)
        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(FlightTable)
        }
    }
}
