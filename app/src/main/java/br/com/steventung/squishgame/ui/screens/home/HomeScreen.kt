package br.com.steventung.squishgame.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.ui.screens.dialogs.TopFivePlayersDialog
import br.com.steventung.squishgame.ui.theme.PinkSquish
import br.com.steventung.squishgame.utils.toTimeString

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToGameScreen: () -> Unit = {},
    onNavigateToInstructionsScreen: () -> Unit = {}
) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.squish_title),
                contentDescription = "squish game title",
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(R.drawable.squishgame_image),
                contentDescription = "squish game logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { onNavigateToGameScreen() },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RectangleShape,
                elevation = ButtonDefaults.buttonElevation(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinkSquish)
            ) {
                Text(text = "Start Game", fontSize = 22.sp)
            }

            Button(
                onClick = { onNavigateToInstructionsScreen() },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                elevation = ButtonDefaults.buttonElevation(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PinkSquish)
            ) {
                Text(text = "Game Instructions", fontSize = 18.sp)
            }
        }
        if (state.topFivePlayersList.isNotEmpty()) {
            FastestPlayerSection(
                state = state,
                onOpenTopFivePlayersTable = {
                    viewModel.setTopFivePlayersVisibility(true)
                }
            )
        }
        if (state.isShowTopFiveDialog) {
            TopFivePlayersDialog(
                gameList = state.topFivePlayersList,
                onCloseDialog = { viewModel.setTopFivePlayersVisibility(false) }
            )
        }
    }
}

@Composable
private fun FastestPlayerSection(
    state: HomeUiState,
    onOpenTopFivePlayersTable: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.prize_image),
            contentDescription = "prize image",
            modifier = Modifier.height(80.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Fastest Player",
                color = Color.Yellow,
                fontSize = 30.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily.Serif
            )
            Text(
                text = state.fastestPlayerName,
                color = Color.White,
                fontSize = 22.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(500)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "timer icon",
                    tint = Color.White
                )
                Text(
                    text = state.fastestGameTime?.toTimeString() ?: "",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(500)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Yellow)
                    .padding(2.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "star icon",
                    tint = Color.Yellow
                )
                Text(
                    text = "Top 5 players",
                    color = Color.Yellow,
                    fontSize = 16.sp,
                    modifier = Modifier.clickable {
                        onOpenTopFivePlayersTable()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}