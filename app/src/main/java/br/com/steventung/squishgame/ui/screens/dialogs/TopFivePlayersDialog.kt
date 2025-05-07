package br.com.steventung.squishgame.ui.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.steventung.squishgame.domain.model.Game
import br.com.steventung.squishgame.ui.theme.LightYellow
import br.com.steventung.squishgame.ui.theme.PinkSquish
import br.com.steventung.squishgame.ui.theme.Purple80
import br.com.steventung.squishgame.ui.theme.PurpleGrey80
import br.com.steventung.squishgame.utils.toTimeString

@Composable
fun TopFivePlayersDialog(
    gameList: List<Game?>,
    onCloseDialog: () -> Unit = {}
) {
    Dialog(onDismissRequest = { onCloseDialog() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = PinkSquish),
            ) {
                Text(
                    text = "Top 5 Players",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = LightYellow,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)) {
                    Text(text = "Player Name", modifier = Modifier.weight(1f), fontSize = 18.sp)
                    Text(text = "Game Time", fontSize = 18.sp)
                }
                gameList.forEachIndexed { index, topPlayerGame ->
                    topPlayerGame?.let {
                        TopPlayerItem(
                            playerName = it.gameUserName,
                            playerScoreTime = it.scoreTime,
                            position = index + 1
                        )
                    }
//                    if (index < 4) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
//                    }
                }
            }
        }
    }
}

@Composable
private fun TopPlayerItem(
    playerName: String,
    playerScoreTime: Long,
    position: Int
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(color = if (position % 2 == 0) PurpleGrey80 else Purple80)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = buildAnnotatedString {
                if (playerName.length <= 15) {
                    append("#$position - $playerName")
                } else {
                    append("#$position - ${playerName.take(15)}...")
                }
            },
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Timer,
                contentDescription = "timer icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = playerScoreTime.toTimeString(),
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopFivePlayersDialogPreview() {
    TopFivePlayersDialog(
        gameList = listOf(
            Game(
                id = "game1",
                gameUserName = "Usuario1",
                scoreTime = 34000L
            ),
            Game(
                id = "game2",
                gameUserName = "Usuario2",
                scoreTime = 48000L
            ),
            Game(
                id = "game3",
                gameUserName = "Usuario3",
                scoreTime = 55000L
            ),
            Game(
                id = "game4",
                gameUserName = "Usuario4",
                scoreTime = 58000L
            ),
            Game(
                id = "game5",
                gameUserName = "Usuario5555555555555555555555555",
                scoreTime = 67000L
            )
        )
    )
}