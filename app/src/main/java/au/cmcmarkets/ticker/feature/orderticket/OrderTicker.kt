package au.cmcmarkets.ticker.feature.orderticket

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.MutableLiveData
import au.cmcmarkets.ticker.R
import au.cmcmarkets.ticker.data.model.Ticker
import au.cmcmarkets.ticker.ui.component.CancelButton
import au.cmcmarkets.ticker.ui.component.ConfirmButton
import au.cmcmarkets.ticker.ui.component.EditText
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


@Composable
fun OrderTicker(
    modifier: Modifier = Modifier,
    onConfirmClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSwipeMarket: () -> Unit,
    onUnitsValueChange: (String) -> Unit,
    onAmountValueChange: (String) -> Unit
) {
    val ticker = remember {
        MutableLiveData(
            Ticker(
                fifteen = BigDecimal.ONE,
                last = BigDecimal(123),
                buy = BigDecimal(7450.79),
                sell = BigDecimal(123),
                symbol = "GBP"
            )
        )
    }
    Scaffold(
        bottomBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(BackgroundBottom)
            ) {
                CancelButton(onCancelClick)
                ConfirmButton(onConfirmClick)
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
                text = "UK 100 - Cash ${ticker.value?.symbol}",
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundProfit)
            )
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                Sell(ticker.value?.sell.toString(), "L: ${BigDecimal("7432.94")}") {
                    //TODO implement onSell
                }
                Buy(ticker.value?.buy.toString(), "H: 7460.94") {
                    //TODO implement onBuy
                }
            }
            Units(
                ticker.value?.buy ?: BigDecimal.ZERO,
                modifier,
                onUnitsValueChange,
                onAmountValueChange,
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
    price: BigDecimal = BigDecimal.ZERO,
    modifier: Modifier = Modifier,
    onUnitsValueChange: (String) -> Unit,
    onAmountValueChange: (String) -> Unit,
    onSwipeMarket: () -> Unit
) {
    var calculation = rememberSaveable { mutableStateOf("") }
    val unitsTextValue = remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .background(BackgroundUnits)
            .padding(6.dp)
    ) {
        Column(modifier.fillMaxWidth(0.35f)) {
            Text(
                text = stringResource(id = R.string.units),
                color = LightBlue,
                fontSize = 18.sp,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            EditText(
                onValueChange = {
                    unitsTextValue.value = it
                    onUnitsValueChange(it)
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            )
        }
        Column(modifier.fillMaxWidth(0.6f)) {
            Text(
                text = stringResource(id = R.string.amount),
                fontSize = 18.sp,
                color = LightBlue,
                modifier = modifier.align(Alignment.CenterHorizontally)
            )
            EditText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                newValue = unitsTextValue.value,
                onValueChange = { onAmountValueChange(it) },
            )
        }
        Column(modifier.fillMaxWidth()) {
            SwipeMarket() { onSwipeMarket() }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderTickerPreview() {
    ComposeSampleTheme {
        OrderTicker(Modifier, {}, {}, {}, {}, {})
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
        Units(BigDecimal.ZERO, Modifier, {}, {}, {})
    }
}