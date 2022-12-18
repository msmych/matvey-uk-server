package uk.matvey.cards

import org.jooq.generated.Tables.CARD
import org.jooq.generated.tables.records.CardRecord
import uk.matvey.cards.Card.Type
import uk.matvey.persistence.entity.AuditedEntityRepo
import uk.matvey.persistence.jooq.JooqRepo
import java.net.URI
import java.util.UUID

class CardRepo(jooqRepo: JooqRepo) : AuditedEntityRepo<CardId, Card, UUID, CardRecord>(jooqRepo, CARD, CARD.ID, CARD.UPDATED_AT) {

    fun findAll(): Collection<Card> {
        return findAllWhere(orderBy = listOf(CARD.UPDATED_AT.desc()))
    }

    override fun Card.toRecord(): CardRecord {
        return CardRecord(this.id.value, this.type.name, this.title, this.url.toString(), this.createdAt, this.updatedAt)
    }

    override fun CardRecord.toEntity(): Card {
        return Card(
            CardId(this.getId()),
            Type.valueOf(this.type),
            this.title,
            this.url?.let(::URI),
            this.getCreatedAt(),
            this.getUpdatedAt(),
        )
    }
}