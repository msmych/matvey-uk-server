package uk.matvey.aperol.entity

import java.time.Instant

abstract class Entity<ID : Comparable<ID>>(
    open val id: ID,
    open val createdAt: Instant,
    open val updatedAt: Instant
)