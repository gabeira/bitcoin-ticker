package au.cmcmarkets.ticker.data.model

import java.math.BigDecimal

data class Order(
    var isBuy: Boolean,
    var time: Long,
    val price: BigDecimal,
    val units: BigDecimal,
    var amount: BigDecimal,
    var symbol: String
)
