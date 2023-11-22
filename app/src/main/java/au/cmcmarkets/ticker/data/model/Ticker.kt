package au.cmcmarkets.ticker.data.model

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class Ticker(
    @SerializedName("15m") var fifteen: BigDecimal,
    val last: BigDecimal,
    var buy: BigDecimal,
    var sell: BigDecimal,
    var symbol: String
) {
    val spread: BigDecimal
        get() = buy.minus(sell)
}