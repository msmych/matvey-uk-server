package uk.matvey.cards

import uk.matvey.cards.Card.Companion.card
import java.net.URI

class CardCommands(private val repo: CardRepo) {

    fun create(type: Card.Type, title: String, url: URI?): Card {
        return repo.add(card(type, title, url))
    }

    fun update(id: CardId, title: String?, url: URI?): Card? {
        val card = repo.get(id)
        return card.copy(
            title = title ?: card.title,
            url = url ?: card.url
        ).takeUnless { it == card }?.let(repo::update)
    }
}