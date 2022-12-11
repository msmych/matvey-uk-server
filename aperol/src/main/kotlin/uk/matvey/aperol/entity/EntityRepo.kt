package uk.matvey.aperol.entity

import org.jooq.Condition
import org.jooq.OrderField
import org.jooq.Table
import org.jooq.TableField
import org.jooq.impl.DSL.noCondition
import uk.matvey.aperol.jooq.EntityRecord
import uk.matvey.aperol.jooq.IdRepo
import uk.matvey.aperol.jooq.JooqRepo
import uk.matvey.aperol.jooq.JooqRepo.Companion.DEFAULT_LIMIT
import java.time.Instant

abstract class EntityRepo<E : Entity<ID>, R : EntityRecord<R, ID>, ID : Comparable<ID>>(
    private val jooqRepo: JooqRepo,
    private val table: Table<R>,
    private val idField: TableField<R, ID>,
    private val updatedAtField: TableField<R, Instant>,
) : IdRepo<R, ID>(jooqRepo, table, idField) {

    fun add(entity: E): E {
        val instant = Instant.now()
        return jooqRepo.add(table, entity.toRecord().setCreatedAt(instant).setUpdatedAt(instant)).toEntity()
    }

    fun update(entity: E): E {
        return jooqRepo.update(
            table,
            entity.toRecord().setUpdatedAt(Instant.now()),
            idField.eq(entity.id).and(updatedAtField.eq(entity.updatedAt))
        )
            .toEntity()
    }

    fun find(id: ID): E? {
        return findById(id)?.toEntity()
    }

    fun get(id: ID): E {
        return getById(id).toEntity()
    }

    fun findAllWhere(
        condition: Condition = noCondition(),
        orderBy: List<OrderField<*>> = listOf(),
        limit: Int = DEFAULT_LIMIT
    ): Collection<E> {
        return jooqRepo.findAllWhere(table, condition, orderBy, limit).map { it.toEntity() }
    }

    abstract fun E.toRecord(): R

    abstract fun R.toEntity(): E
}