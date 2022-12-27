package uk.matvey.support.json

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import uk.matvey.support.json.JsonElementSupport.asInt
import uk.matvey.support.json.JsonElementSupport.asJsonArray
import uk.matvey.support.json.JsonElementSupport.asJsonObject
import uk.matvey.support.json.JsonElementSupport.asJsonPrimitive
import uk.matvey.support.json.JsonElementSupport.asLong
import uk.matvey.support.json.JsonElementSupport.asString
import uk.matvey.support.string.StringSupport.decimal
import uk.matvey.support.string.StringSupport.instant
import uk.matvey.support.string.StringSupport.uuid
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

object JsonObjectSupport {

    fun JsonObject.uuid(name: String): UUID = string(name).uuid()

    fun JsonObject.uuidOpt(name: String): UUID? = stringOpt(name)?.uuid()

    fun JsonObject.instant(name: String): Instant = string(name).instant()

    fun JsonObject.instantOpt(name: String): Instant? = stringOpt(name)?.instant()

    fun JsonObject.decimal(name: String): BigDecimal = req(name).asJsonPrimitive().content.decimal()

    fun JsonObject.decimalOpt(name: String): BigDecimal? = opt(name)?.asJsonPrimitive()?.content?.decimal()

    fun JsonObject.stringArr(name: String): List<String> = arr(name).map { it.asString() }

    fun JsonObject.stringArrOpt(name: String): List<String>? = arrOpt(name)?.map { it.asString() }

    fun JsonObject.string(name: String): String = req(name).asString()

    fun JsonObject.stringOpt(name: String): String? = opt(name)?.asString()

    fun JsonObject.long(name: String): Long = req(name).asLong()

    fun JsonObject.longOpt(name: String): Long? = opt(name)?.asLong()

    fun JsonObject.int(name: String): Int = req(name).asInt()

    fun JsonObject.intOpt(name: String): Int? = opt(name)?.asInt()

    fun JsonObject.obj(name: String): JsonObject = req(name).asJsonObject()

    fun JsonObject.objOpt(name: String): JsonObject? = opt(name)?.asJsonObject()

    fun JsonObject.arr(name: String): JsonArray = req(name).asJsonArray()

    fun JsonObject.arrOpt(name: String): JsonArray? = opt(name)?.asJsonArray()

    fun JsonObject.req(name: String): JsonElement = requireNotNull(opt(name)) {
        "Not found JSON value [key = $name]"
    }

    fun JsonObject.opt(name: String): JsonElement? = get(name).takeUnless { it is JsonNull }
}
