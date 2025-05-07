package br.com.steventung.squishgame.ui.screens.instructions

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.ui.theme.DarkRed
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionsScreen(
    onBackNavigation: () -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val density = LocalDensity.current

    val runningOffsetX = remember { Animatable(0f) }
    val runningTime = 10_000

    val failureOffSet = remember { Animatable(0f) }
    val failureTime = 5_000
    var isFailure by remember { mutableStateOf(false) }
    var isStaring by remember { mutableStateOf(false) }

    var showFirstImage by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            showFirstImage = !showFirstImage
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            runningOffsetX.snapTo(0f)
            runningOffsetX.animateTo(
                targetValue = with(density) { (screenWidth - 100.dp).toPx() },
                animationSpec = tween(durationMillis = runningTime, easing = LinearEasing)
            )
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            failureOffSet.snapTo(0f)
            failureOffSet.animateTo(
                targetValue = with(density) { ((screenWidth - 100.dp) / 3).toPx() },
                animationSpec = tween(durationMillis = failureTime, easing = LinearEasing)
            )
            delay(3000)
            isFailure = false
            isStaring = false
        }
    }

    LaunchedEffect(failureOffSet.value) {
        if (failureOffSet.value >= with(density) { ((screenWidth - 100.dp) / 4).toPx() }) {
            isStaring = true
            delay(500)
            isFailure = true
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Game Instructions",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkRed
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back navigation icon",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(30.dp)
                            .clickable { onBackNavigation() },
                        tint = DarkRed
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.LightGray)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = Color.DarkGray)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "1 - When starting the game, make sure the camera is positioned on" +
                            " a stable surface such as a table or a tripod.",
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Text(
                    text = "2 - Position your body so that your head, shoulders, and hands are " +
                            "always visible on camera.",
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Text(
                    text = "3 - To move the character, swing your arms alternately as if you " +
                            "were running.",
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Crossfade(
                    targetState = showFirstImage,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    label = "",
                ) { isFirst ->
                    val imageRes =
                        if (isFirst) R.drawable.player_right_arm_up else R.drawable.player_left_arm_up
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                    )
                }
                Text(
                    text = "4 - Move only when the doll is singing and has its eyes closed.",
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(R.drawable.player_running),
                        contentDescription = "player running image",
                        modifier = Modifier
                            .height(150.dp)
                            .align(Alignment.CenterStart)
                            .graphicsLayer {
                                translationX = runningOffsetX.value
                            }
                    )
                    Image(
                        painter = painterResource(R.drawable.doll_eyes_closed2),
                        contentDescription = "image doll singing image",
                        modifier = Modifier
                            .height(150.dp)
                            .align(Alignment.CenterEnd)
                    )
                }
                Text(
                    text = "5 - When the doll stops singing and turns to face the player, " +
                            "stay still — otherwise, your character will be eliminated.",
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (isFailure) {
                        Image(
                            painter = painterResource(R.drawable.player_down),
                            contentDescription = "player running image",
                            modifier = Modifier
                                .height(150.dp)
                                .offset(y = 50.dp)
                                .align(Alignment.CenterStart)
                                .graphicsLayer {
                                    translationX = failureOffSet.value
                                }
                        )
                        Image(
                            painter = painterResource(R.drawable.doll_shooting),
                            contentDescription = "image doll singing image",
                            modifier = Modifier
                                .height(150.dp)
                                .align(Alignment.CenterEnd)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.player_running),
                            contentDescription = "player running image",
                            modifier = Modifier
                                .height(150.dp)
                                .align(Alignment.CenterStart)
                                .graphicsLayer {
                                    translationX = failureOffSet.value
                                }
                        )
                        Image(
                            painter = painterResource(
                                if (isStaring) R.drawable.doll_facing_left
                                else R.drawable.doll_eyes_closed2
                            ),
                            contentDescription = "image doll singing image",
                            modifier = Modifier
                                .height(150.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
                Text(
                    text = "6 - The objective of the game is to pass the doll's position " +
                            "in the shortest time possible without being eliminated.",
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InstructionsScreenPreview() {
    InstructionsScreen()
}
