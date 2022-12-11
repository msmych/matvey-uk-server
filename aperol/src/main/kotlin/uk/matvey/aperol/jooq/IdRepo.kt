package uk.matvey.aperol.jooq

import org.jooq.Record
import org.jooq.Table
import org.jooq.TableField

open class IdRepo<R : Record, ID : Comparable<ID>>(
    private val jooqRepo: JooqRepo,
    private val table: Table<R>,
    private val idField: TableField<R, ID>,
) {

    fun findById(id: ID): R? {
        return jooqRepo.findOneWhere(table, idField.eq(id))
    }

    fun getById(id: ID): R {
        return findById(id) ?: throw IllegalArgumentException("Not found ${table.name} [id=$id]")
    }
}