package uk.matvey.cards

import uk.matvey.support.persistence.entity.Entity
import java.util.UUID

class CardId(override val value: UUID) : Entity.Id<UUID>(value)
