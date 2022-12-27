package uk.matvey.support.string

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

object StringSupport {

    fun String.decimal(): BigDecimal = BigDecimal(this)

    fun String.uuid(): UUID = UUID.fromString(this)

    fun String.instant(): Instant = Instant.parse(this)
}
