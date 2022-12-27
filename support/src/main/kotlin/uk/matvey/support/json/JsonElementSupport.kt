package uk.matvey.support.json

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.longOrNull

object JsonElementSupport {

    fun JsonElement.asString(): String {
        val primitive = asJsonPrimitive()
        require(primitive.isString) { "Expected JSON string value but found $this" }
        return primitive.content
    }

    fun JsonElement.asLong(): Long {
        val primitive = asJsonPrimitive()
        return requireNotNull(primitive.longOrNull) { "Expected JSON long value but found $this" }
    }

    fun JsonElement.asInt(): Int {
        val primitive = asJsonPrimitive()
        return requireNotNull(primitive.intOrNull) { "Expected JSON int value but found $this" }
    }

    fun JsonElement.asJsonObject(): JsonObject {
        require(this is JsonObject) { "Expected JSON object value but found $this" }
        return this
    }

    fun JsonElement.asJsonArray(): JsonArray {
        require(this is JsonArray) { "Expected JSON array value but found $this" }
        return this
    }

    fun JsonElement.asJsonPrimitive(): JsonPrimitive {
        require(this is JsonPrimitive) { "Expected JSON primitive value but found $this" }
        return this
    }
}
