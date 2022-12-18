package uk.matvey.cards

class CardQueries(private val repo: CardRepo) {

    fun findAll(): Collection<Card> {
        return repo.findAll()
    }

    fun find(id: CardId): Card? {
        return repo.find(id)
    }
}