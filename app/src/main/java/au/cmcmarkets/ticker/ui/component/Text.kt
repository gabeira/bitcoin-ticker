package au.cmcmarkets.ticker.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.cmcmarkets.ticker.ui.theme.BackgroundProfit
import au.cmcmarkets.ticker.ui.theme.BackgroundUnits
import au.cmcmarkets.ticker.ui.theme.ComposeSampleTheme
import au.cmcmarkets.ticker.ui.theme.DarkYellow
import au.cmcmarkets.ticker.ui.theme.GreenPrice
import au.cmcmarkets.ticker.ui.theme.LightBlue


@Composable
fun TextTicker(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 34.sp,
        maxLines = 1,
        softWrap = false,
        color = GreenPrice,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 2.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TextTickerPreview() {
    ComposeSampleTheme {
        TextTicker("4323.42", Modifier)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditTextTopTitle(
    modifier: Modifier = Modifier,
    title: String,
    isBuy: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {
    Column(modifier) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = if (isBuy) LightBlue else DarkYellow,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        EditText(isBuy = isBuy, value = value, onValueChange = onValueChange)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditText(
    modifier: Modifier = Modifier,
    isBuy: Boolean,
    value: String,
    onValueChange: (String) -> Unit
) {
    val controller = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .sizeIn(maxHeight = 60.dp)
            .padding(horizontal = 4.dp),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            baselineShift = BaselineShift.Superscript,
            textAlign = TextAlign.Center,
            lineHeight = 30.sp
        ),
        isError = false,
        visualTransformation = VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        keyboardActions = KeyboardActions(onDone = { controller?.hide() }),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isBuy) LightBlue else DarkYellow,
            unfocusedBorderColor = BackgroundUnits,
            focusedContainerColor = BackgroundProfit,
            unfocusedContainerColor = BackgroundProfit,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EditTextPreview() {
    ComposeSampleTheme {
        Column(Modifier.background(BackgroundUnits)) {
            EditTextTopTitle(Modifier, "Test", true, "") {}
        }
    }
}