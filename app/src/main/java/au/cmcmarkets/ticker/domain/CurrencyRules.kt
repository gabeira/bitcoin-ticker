package au.cmcmarkets.ticker.domain

import java.math.BigDecimal

fun BigDecimal.calculateAmountWith(units: String): String =
    this.multiply(BigDecimal(units)).toString()

fun String.calculateUnitsWith(price: BigDecimal): String {
//    return BigDecimal(this).divide(price).toString()
    /**
     * If the user types in a value in the amount field,
     * the app should calculate the correct units value
     * and populate the units field also (BUY price / amount value).
     */
    //TODO review rule, shouldn't it be amount/price?
    return price.divide(BigDecimal(this)).toString()
}