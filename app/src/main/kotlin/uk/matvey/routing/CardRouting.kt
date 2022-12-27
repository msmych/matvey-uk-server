package uk.matvey.routing

import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import uk.matvey.cards.Card
import uk.matvey.cards.CardCommands
import uk.matvey.cards.CardId
import uk.matvey.cards.CardQueries
import uk.matvey.support.json.JsonObjectSupport.string
import uk.matvey.support.json.JsonObjectSupport.stringOpt
import uk.matvey.support.server.ktor.ApplicationCallSupport.jsonObj
import uk.matvey.support.server.ktor.ApplicationCallSupport.uuidPathParam
import java.net.URI

fun Route.cardRouting(cardQueries: CardQueries, cardCommands: CardCommands) {
    route("/cards") {
        get {
            call.respond(buildJsonArray {
                cardQueries.findAll().forEach { add(it.toJson()) }
            })
        }
        post {
            val payload = call.jsonObj()
            val card = cardCommands.create(
                Card.Type.valueOf(payload.string("type")),
                payload.string("title"),
                payload.stringOpt("url")?.let(::URI)
            )
            call.respond(buildJsonObject { put("id", card.id.toString()) })
        }
        route("/{id}") {
            get {
                val id = call.uuidPathParam("id")
                cardQueries.find(CardId(id))?.let { card ->
                    call.respond(card.toJson())
                }
            }
            patch {
                val id = CardId(call.uuidPathParam("id"))
                val payload = call.jsonObj()
                cardCommands.update(
                    id,
                    payload.string("title"),
                    payload.stringOpt("url")?.let(::URI)
                )?.let {
                    call.respond(NoContent)
                }
            }
        }
    }
}