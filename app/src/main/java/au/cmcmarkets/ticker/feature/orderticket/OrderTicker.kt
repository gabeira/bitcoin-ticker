package au.cmcmarkets.ticker.feature.orderticket

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.cmcmarkets.ticker.R
import au.cmcmarkets.ticker.data.model.Order
import au.cmcmarkets.ticker.data.model.Ticker
import au.cmcmarkets.ticker.domain.calculateAmountWith
import au.cmcmarkets.ticker.domain.calculateUnitsWith
import au.cmcmarkets.ticker.ui.component.CancelButton
import au.cmcmarkets.ticker.ui.component.ConfirmButton
import au.cmcmarkets.ticker.ui.component.EditTextTopTitle
import au.cmcmarkets.ticker.ui.component.SwipeMarket
import au.cmcmarkets.ticker.ui.component.TextTicker
import au.cmcmarkets.ticker.ui.theme.Background
import au.cmcmarkets.ticker.ui.theme.BackgroundBottom
import au.cmcmarkets.ticker.ui.theme.BackgroundProfit
import au.cmcmarkets.ticker.ui.theme.BackgroundUnits
import au.cmcmarkets.ticker.ui.theme.ComposeSampleTheme
import au.cmcmarkets.ticker.ui.theme.DarkYellow
import au.cmcmarkets.ticker.ui.theme.GreenPrice
import au.cmcmarkets.ticker.ui.theme.LightBlue
import au.cmcmarkets.ticker.ui.theme.RedPrice
import java.math.BigDecimal
import java.util.Calendar


@Composable
fun OrderTicker(
    viewModel: OrderTicketViewModel,
    modifier: Modifier = Modifier,
    onConfirmClick: (Order) -> Unit,
    onCancelClick: () -> Unit,
    onSwipeMarket: () -> Unit
) {
    var orderTypeBuy by rememberSaveable { mutableStateOf(true) }
    var units by rememberSaveable { mutableStateOf("") }
    var amount by rememberSaveable { mutableStateOf("") }
    var btConfirmEnabled by rememberSaveable { mutableStateOf(false) }
    val ticker = viewModel.ticker.observeAsState().value
    Scaffold(
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(BackgroundBottom)
            ) {
                CancelButton(onCancelClick)
                ConfirmButton(btConfirmEnabled) {
                    Log.e(
                        "OrderTIcker",
                        "Confirm ${if (orderTypeBuy) "Buy" else "Sell"}" +
                                " order with $units units and amount $amount"
                    )
                    ticker?.let {//TODO implement order
                        try {
                            onConfirmClick(
                                Order(
                                    orderTypeBuy,
                                    Calendar.getInstance().timeInMillis,
                                    it.buy,
                                    BigDecimal(units),
                                    BigDecimal(amount),
                                    it.symbol
                                )
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier
                .fillMaxHeight()
                .background(Background)
                .padding(paddingValues)
        ) {
            Text(
                text = "UK 100 - Cash ${ticker?.symbol}",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundProfit)
            )
            Box {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(Color.Black)
                ) {
                    Sell(
                        ticker?.sell.toString(),
                        "L: ${ticker?.fifteen.toString()}"
                    ) {
                        //TODO implement blink onSell
                        orderTypeBuy = false
                    }
                    Buy(
                        ticker?.buy.toString(),
                        "H: ${ticker?.fifteen.toString()}"
                    ) {
                        //TODO implement blink onBuy
                        orderTypeBuy = true
                    }
                }
                Text(
                    text = ticker?.spread.toString(),
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .background(BackgroundProfit)
                        .align(Alignment.BottomCenter)
                )
            }
            Units(
                modifier,
                ticker,
                orderTypeBuy,
                onUnitsValueChange = {
                    if (it.isNotEmpty()) {
                        btConfirmEnabled = true
                        units = it
                    }
                },
                onAmountValueChange = {
                    if (it.isNotEmpty()) {
                        btConfirmEnabled = true
                        amount = it
                    }
                },
                onSwipeMarket
            )
            Spacer(
                Modifier
                    .size(6.dp)
                    .background(Background)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f)
                    .background(BackgroundProfit)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add),
                    "",
                    colorFilter = ColorFilter.tint(color = RedPrice)
                )
                Text(
                    text = stringResource(id = R.string.stop_loss),
                    color = RedPrice
                )
            }
            Spacer(
                Modifier
                    .size(6.dp)
                    .background(Background)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f)
                    .background(BackgroundProfit)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.add), "",
                    colorFilter = ColorFilter.tint(color = GreenPrice)
                )
                Text(
                    text = stringResource(id = R.string.take_profit),
                    color = GreenPrice
                )
            }
            Spacer(
                Modifier
                    .size(6.dp)
                    .background(Background)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f)
                    .background(BackgroundProfit)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron), "",
                    colorFilter = ColorFilter.tint(color = Color.White)
                )
                Text(
                    text = stringResource(id = R.string.estimated_margin),
                    color = Color.White
                )
                Text(
                    text = "0.00",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Buy(
    price: String,
    highPrice: String,
    modifier: Modifier = Modifier,
    onBuyClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {
                onBuyClick(price)
            }
    ) {
        Text(
            text = stringResource(id = R.string.buy),
            fontSize = 14.sp,
            color = LightBlue,
            modifier = modifier.align(Alignment.End)
        )
        TextTicker(
            text = price,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = highPrice,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = modifier.align(Alignment.End)
        )
    }
}

@Composable
fun Sell(
    price: String,
    lowPrice: String,
    modifier: Modifier = Modifier,
    onSellClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth(0.5f)
            .clickable { onSellClick(price) }
    ) {
        Text(
            text = stringResource(id = R.string.sell),
            color = DarkYellow,
            fontSize = 14.sp,
            modifier = modifier.align(Alignment.Start)
        )
        TextTicker(
            text = price,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = lowPrice,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = modifier.align(Alignment.Start)
        )
    }
}

@Composable
fun Units(
    modifier: Modifier = Modifier,
    ticker: Ticker?,
    isBuy: Boolean,
    onUnitsValueChange: (String) -> Unit,
    onAmountValueChange: (String) -> Unit,
    onSwipeMarket: () -> Unit
) {
    val price = if (isBuy) ticker?.buy ?: BigDecimal.ZERO
    else ticker?.sell ?: BigDecimal.ZERO

    var unitsValue by rememberSaveable { mutableStateOf("") }
    var amountValue: String by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = modifier
            .background(BackgroundUnits)
            .padding(6.dp)
    ) {
        EditTextTopTitle(
            modifier = modifier.fillMaxWidth(0.35f),
            stringResource(id = R.string.units),
            isBuy,
            value = unitsValue
        ) { unitsSelected ->
            unitsValue = unitsSelected
            amountValue = try {
                price.calculateAmountWith(unitsSelected)
            } catch (e: Exception) {
                e.printStackTrace()
                "0"
            }
            onUnitsValueChange(unitsSelected)
        }
        EditTextTopTitle(
            modifier = modifier.fillMaxWidth(0.6f),
            title = stringResource(id = R.string.amount),
            isBuy,
            value = amountValue
        ) {
            amountValue = it
            unitsValue = try {
                it.calculateUnitsWith(price)
            } catch (e: Exception) {
                e.printStackTrace()
                "0"
            }
            onAmountValueChange(it)
        }
        Column(modifier.fillMaxWidth()) {
            SwipeMarket(isBuy) { onSwipeMarket() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderTickerPreview() {
    ComposeSampleTheme {
//        OrderTicker(Modifier, {}, {}, {}, {}, {})
    }
}

@Preview(showBackground = true)
@Composable
fun BuyPreview() {
    ComposeSampleTheme {
        Buy("1234.56", "H: 74.60.94") {}
    }
}

//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SellPreview() {
    ComposeSampleTheme {
        Sell("1234.56", "L: 74.32.94") {}
    }
}

@Preview(showBackground = true)
@Composable
fun UnitsPreview() {
    ComposeSampleTheme {
        Units(Modifier, null, false, {}, {}, {})
    }
}