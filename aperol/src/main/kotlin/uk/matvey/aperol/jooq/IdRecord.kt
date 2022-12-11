package uk.matvey.aperol.jooq

import org.jooq.Record

interface IdRecord<R : Record, ID : Comparable<ID>> : Record {

    fun getId(): ID

    fun setId(id: ID): R
}