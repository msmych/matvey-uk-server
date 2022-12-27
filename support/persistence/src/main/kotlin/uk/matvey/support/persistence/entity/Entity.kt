package uk.matvey.support.persistence.entity

abstract class Entity<ID : Entity.Id<*>>(
    open val id: ID
) {

    open class Id<ID : Comparable<ID>>(open val value: ID)
}
