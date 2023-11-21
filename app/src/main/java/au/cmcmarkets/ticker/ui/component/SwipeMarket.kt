package au.cmcmarkets.ticker.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.cmcmarkets.ticker.R
import au.cmcmarkets.ticker.ui.theme.ComposeSampleTheme
import au.cmcmarkets.ticker.ui.theme.LightBlue

@Composable
fun SwipeMarket(modifier: Modifier = Modifier, onSwipe: () -> Unit) {
    Box(
        modifier = modifier
            .sizeIn(maxHeight = 80.dp)
            .padding(6.dp)
            .background(color = Color.Transparent)
            .clip(RoundedCornerShape(percent = 20))
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.market),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .background(color = LightBlue)
            )
            Image(
                painter = painterResource(id = R.drawable.more),
                contentDescription = stringResource(id = R.string.swipe_market),
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.Gray)
                    .clickable { onSwipe() } //TODO implement Swipe Market
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SwipeMarketPreview() {
    ComposeSampleTheme {
        SwipeMarket(Modifier) {}
    }
}
