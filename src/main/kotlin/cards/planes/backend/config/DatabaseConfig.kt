package cards.planes.backend.config

import cards.planes.backend.flight.FlightTable
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfig {

    @Bean
    fun database(): Database {
        val db = Database.connect(
            url = "jdbc:h2:mem:flights;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
        )
        transaction(db) {
            SchemaUtils.create(FlightTable)
        }
        return db
    }
}
