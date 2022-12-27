package uk.matvey.support.int

import java.math.BigDecimal

object IntSupport {

    fun Int.decimal(): BigDecimal = BigDecimal(this)
}
