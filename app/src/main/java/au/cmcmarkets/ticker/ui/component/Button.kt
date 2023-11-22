package au.cmcmarkets.ticker.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.cmcmarkets.ticker.R
import au.cmcmarkets.ticker.ui.theme.CancelBackground
import au.cmcmarkets.ticker.ui.theme.ComposeSampleTheme
import au.cmcmarkets.ticker.ui.theme.ConfirmBackground

@Composable
fun CancelButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = CancelBackground,
            contentColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(4.dp),
        shape = RoundedCornerShape(percent = 20)
    ) {
        Text(text = stringResource(id = R.string.cancel))
    }
}

@Composable
fun ConfirmButton(btEnabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = ConfirmBackground,
            contentColor = Color.LightGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        enabled = btEnabled,
        shape = RoundedCornerShape(percent = 20)
    ) {
        Text(text = stringResource(id = R.string.confirm))
    }
}

@Preview(showBackground = true)
@Composable
fun CancelConfirmButtonPreview() {
    ComposeSampleTheme {
        Row {
            CancelButton { }
            ConfirmButton(true) { }
        }
    }
}