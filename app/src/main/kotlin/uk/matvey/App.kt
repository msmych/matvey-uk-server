package uk.matvey

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import mu.KotlinLogging
import uk.matvey.persistence.jooq.JooqRepo
import uk.matvey.cards.CardCommands
import uk.matvey.cards.CardQueries
import uk.matvey.cards.CardRepo
import uk.matvey.routing.cardRouting

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

private val logger = KotlinLogging.logger {}

fun main() {
    logger.info { "<<< Starting engine >>>" }
    embeddedServer(factory = Netty, port = 8000, module = Application::serverModule).start(wait = true)
}

fun Application.serverModule() {
    install(ContentNegotiation) {
        json()
    }
    routing {
        route("/api") {
            val repo = JooqRepo(
                HikariDataSource(HikariConfig().apply {
                    jdbcUrl = "jdbc:postgresql://localhost:55000/postgres"
                    username = "postgres"
                    password = "postgres"
                    driverClassName = "org.postgresql.Driver"
                })
            )
            val cardRepo = CardRepo(repo)
            cardRouting(CardQueries(cardRepo), CardCommands(cardRepo))
        }
    }
}
