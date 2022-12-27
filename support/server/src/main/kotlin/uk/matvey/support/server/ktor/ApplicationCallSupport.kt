package uk.matvey.support.server.ktor

import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receiveText
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import uk.matvey.support.json.JsonElementSupport.asJsonArray
import uk.matvey.support.json.JsonElementSupport.asJsonObject
import uk.matvey.support.string.StringSupport.uuid
import java.util.UUID

object ApplicationCallSupport {

    suspend fun ApplicationCall.jsonObj(): JsonObject = parseToJsonElement(receiveText()).asJsonObject()

    suspend fun ApplicationCall.jsonArr(): JsonArray = parseToJsonElement(receiveText()).asJsonArray()

    fun ApplicationCall.uuidPathParam(name: String): UUID = pathParam(name).uuid()

    fun ApplicationCall.pathParam(name: String): String = requireNotNull(parameters[name]) {
        "Not found path param $name"
    }

    fun ApplicationCall.queryParam(name: String): String = requireNotNull(request.queryParameters[name]) {
        "Not found query param $name"
    }

    fun ApplicationCall.queryParamOpt(name: String): String? = request.queryParameters[name]
}
