package uk.matvey.cards

import java.util.UUID

class CardQueries(private val repo: CardRepo) {

    fun findAll(): Collection<Card> {
        return repo.findAll()
    }

    fun find(id: UUID): Card? {
        return repo.find(id)
    }
}