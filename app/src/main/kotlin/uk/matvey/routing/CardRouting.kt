package uk.matvey.routing

import io.ktor.http.HttpStatusCode.Companion.NoContent
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import uk.matvey.cards.Card
import uk.matvey.cards.CardCommands
import uk.matvey.cards.CardQueries
import java.net.URI
import java.util.UUID

fun Route.cardRouting(cardQueries: CardQueries, cardCommands: CardCommands) {
    route("/cards") {
        get {
            call.respond(buildJsonArray {
                cardQueries.findAll().forEach { add(it.toJson()) }
            })
        }
        post {
            val payload = parseToJsonElement(call.receiveText()).jsonObject
            val card = cardCommands.create(
                Card.Type.valueOf(requireNotNull(payload["type"]).jsonPrimitive.content),
                requireNotNull(payload["title"]).jsonPrimitive.content,
                payload["url"]?.jsonPrimitive?.content?.let(::URI)
            )
            call.respond(buildJsonObject { put("id", card.id.toString()) })
        }
        route("/{id}") {
            get {
                val id = requireNotNull(call.parameters["id"])
                cardQueries.find(UUID.fromString(id))?.let { card ->
                    call.respond(card.toJson())
                }
            }
            patch {
                val id = UUID.fromString(requireNotNull(call.parameters["id"]))
                val payload = parseToJsonElement(call.receiveText()).jsonObject
                cardCommands.update(
                    id,
                    payload["title"]?.jsonPrimitive?.content,
                    payload["url"]?.jsonPrimitive?.content?.let(::URI)
                )?.let {
                    call.respond(NoContent)
                }
            }
        }
    }
}