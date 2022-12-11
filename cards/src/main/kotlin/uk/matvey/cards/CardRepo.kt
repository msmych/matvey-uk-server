package uk.matvey.cards

import org.jooq.generated.Tables.CARD
import org.jooq.generated.tables.records.CardRecord
import uk.matvey.aperol.entity.EntityRepo
import uk.matvey.aperol.jooq.JooqRepo
import uk.matvey.cards.Card.Type
import java.net.URI
import java.util.UUID

class CardRepo(jooqRepo: JooqRepo) : EntityRepo<Card, CardRecord, UUID>(jooqRepo, CARD, CARD.ID, CARD.UPDATED_AT) {

    fun findAll(): Collection<Card> {
        return findAllWhere(orderBy = listOf(CARD.UPDATED_AT.desc()))
    }

    override fun Card.toRecord(): CardRecord {
        return CardRecord(this.id, this.type.name, this.title, this.url.toString(), this.createdAt, this.updatedAt)
    }

    override fun CardRecord.toEntity(): Card {
        return Card(
            this.getId(),
            Type.valueOf(this.type),
            this.title,
            this.url?.let(::URI),
            this.getCreatedAt(),
            this.getUpdatedAt(),
        )
    }
}