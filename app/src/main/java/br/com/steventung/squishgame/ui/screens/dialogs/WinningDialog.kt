package br.com.steventung.squishgame.ui.screens.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.ui.screens.camera.GAME_TIMER
import br.com.steventung.squishgame.ui.theme.DarkRed
import br.com.steventung.squishgame.ui.theme.LightYellow
import br.com.steventung.squishgame.ui.theme.PinkSquish
import br.com.steventung.squishgame.utils.toTimeString

@Composable
fun WinningDialog(
    modifier: Modifier = Modifier,
    onRestartGame: () -> Unit = {},
    onBackToHomeScreen: () -> Unit = {},
    gameTime: Long
) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = modifier
                .paint(painterResource(R.drawable.celebration_background))
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Congratulations, you survived!",
                color = DarkRed,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(R.drawable.player_celebrating),
                contentDescription = "player celebrating image",
                modifier = Modifier.height(270.dp)
            )
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Time",
                    fontWeight = FontWeight.Bold,
                    color = DarkRed,
                    fontSize = 18.sp,
                    modifier = Modifier.background(color = LightYellow)
                )
                Row(
                    modifier = Modifier.background(color = LightYellow),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Timer,
                        contentDescription = "time icon",
                        tint = DarkRed,
                    )
                    Text(
                        text = ": ${(GAME_TIMER - gameTime).toTimeString()}",
                        fontWeight = FontWeight(500),
                        fontSize = 18.sp,
                        color = DarkRed
                    )
                }
            }
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
private fun WinningDialogPreview() {
    WinningDialog(gameTime = 28_000L)
}