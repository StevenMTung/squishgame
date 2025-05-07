package br.com.steventung.squishgame.ui.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.ui.theme.PinkSquish

@Composable
fun LosingDialog(
    modifier: Modifier = Modifier,
    onRestartGame: () -> Unit = {},
    onBackToHomeScreen: () -> Unit = {}
) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = modifier
                .background(brush = Brush.verticalGradient(listOf(Color.DarkGray, Color.LightGray)))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "You failed!",
                color = PinkSquish,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.player_down),
                contentDescription = "player down image",
                modifier = Modifier.height(200.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onRestartGame() },
                    colors = ButtonDefaults.buttonColors(containerColor = PinkSquish)
                ) {
                    Text(text = "Play again")
                }
                Button(
                    onClick = { onBackToHomeScreen() },
                    colors = ButtonDefaults.buttonColors(containerColor = PinkSquish)
                ) {
                    Text(text = "Exit game")
                }
            }
        }
    }
}

@Preview
@Composable
private fun LosingDialogPreview() {
    LosingDialog()
}