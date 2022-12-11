package uk.matvey.aperol.jooq

import org.jooq.Record

interface EntityRecord<R : Record, ID : Comparable<ID>> : IdRecord<R, ID>, AuditedRecord<R>, Record