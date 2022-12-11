package uk.matvey.aperol.jooq

import org.jooq.Record
import java.time.Instant

interface AuditedRecord<R : Record> : Record {

    fun getCreatedAt(): Instant

    fun setCreatedAt(date: Instant): R

    fun getUpdatedAt(): Instant

    fun setUpdatedAt(date: Instant): R
}