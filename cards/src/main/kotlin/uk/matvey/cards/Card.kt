package uk.matvey.cards

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import uk.matvey.aperol.entity.Entity
import java.net.URI
import java.time.Instant
import java.util.UUID
import java.util.UUID.randomUUID

data class Card(
    override val id: UUID,
    val type: Type,
    val title: String,
    val url: URI?,
    override val createdAt: Instant,
    override val updatedAt: Instant
) : Entity<UUID>(id, createdAt, updatedAt) {

    enum class Type {
        URL,
    }

    fun toJson() = buildJsonObject {
        put("id", id.toString())
        put("title", title)
        put("url", url.toString())
    }

    companion object {

        fun card(type: Type, title: String, url: URI?): Card {
            val instant = Instant.now()
            return Card(randomUUID(), type, title, url, instant, instant)
        }
    }
}
