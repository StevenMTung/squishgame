package br.com.steventung.squishgame.ui.screens.game

import android.graphics.PointF
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.ui.screens.camera.CameraUiState
import br.com.steventung.squishgame.ui.screens.camera.CameraViewModel
import br.com.steventung.squishgame.ui.screens.camera.DollState
import br.com.steventung.squishgame.ui.screens.camera.GameState
import br.com.steventung.squishgame.ui.screens.dialogs.HighScoreDialog
import br.com.steventung.squishgame.ui.screens.dialogs.LosingDialog
import br.com.steventung.squishgame.ui.screens.dialogs.WinningDialog
import br.com.steventung.squishgame.ui.theme.PinkSquish
import br.com.steventung.squishgame.utils.toTimeString
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import kotlinx.coroutines.delay

@Composable
fun GameOverlayScreen(
    modifier: Modifier = Modifier,
    pose: Pose,
    viewModel: CameraViewModel,
    state: CameraUiState,
    onBackToHomeScreen: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val density = LocalDensity.current
        val offsetX = remember { Animatable(0f) }

        val steps = state.steps
        val totalSteps = 100
        val stepSizePx =
            with(density) { (screenWidth - 50.dp).toPx() / totalSteps }
        val targetOffset = stepSizePx * steps

        DisposableEffect(Unit) {
            onDispose {
                viewModel.releaseSoundManager()
            }
        }

        LaunchedEffect(steps) {
            offsetX.animateTo(
                targetValue = targetOffset,
                animationSpec = tween(durationMillis = 300)
            )
        }

        LaunchedEffect(state.gameState) {
            if (state.gameState == GameState.OnGame) {
                viewModel.startGameTimerCountDown()
            }
        }

        GameTimer(state)

        LaunchedEffect(state.gameTimer) {
            if (state.gameTimer == 0L) {
                viewModel.loseGame()
            }
        }

        if (state.gameState == GameState.GamePaused) {
            StartGameButton(
                state = state,
                onStartGame = {
                    viewModel.initializeGame()
                },
                onSwitchCheckedChange = {
                    viewModel.setPoseDetectionPointVisibility(it)
                }
            )
        }

        if (state.gameState == GameState.GameCountDown) {
            StartGameTimerCountDown(
                state = state,
                onSwitchCheckedChange = {
                    viewModel.setPoseDetectionPointVisibility(it)
                }
            )
        }

        var rightIndexPosition by remember { mutableStateOf(PointF(0f, 0f)) }
        var rightShoulderPosition by remember { mutableStateOf(PointF(0f, 0f)) }
        var rightElbowPosition by remember { mutableStateOf(PointF(0f, 0f)) }
        var leftIndexPosition by remember { mutableStateOf(PointF(0f, 0f)) }
        var leftShoulderPosition by remember { mutableStateOf(PointF(0f, 0f)) }
        var leftElbowPosition by remember { mutableStateOf(PointF(0f, 0f)) }
        var nosePosition by remember { mutableStateOf(PointF(0f, 0f)) }

        rightIndexPosition =
            pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)?.position ?: PointF(400f, 400f)
        rightShoulderPosition =
            pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)?.position ?: PointF(0f, 0f)
        rightElbowPosition =
            pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)?.position ?: PointF(800f, 800f)
        leftIndexPosition =
            pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)?.position ?: PointF(400f, 400f)
        leftShoulderPosition =
            pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)?.position ?: PointF(0f, 0f)
        leftElbowPosition =
            pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)?.position ?: PointF(800f, 800f)
        nosePosition =
            pose.getPoseLandmark(PoseLandmark.NOSE)?.position ?: PointF(0f, 0f)

        //Draw the pose detection points if the switch is enable
        if (state.isShowPoseDetectionPoints) {
            DrawPosePoints(
                scaleFactor = state.scaleFactor,
                postScaleWidthOffset = state.postScaleWidthOffset,
                rightIndexPosition = rightIndexPosition,
                rightShoulderPosition = rightShoulderPosition,
                rightElbowPosition = rightElbowPosition,
                leftIndexPosition = leftIndexPosition,
                leftShoulderPosition = leftShoulderPosition,
                leftElbowPosition = leftElbowPosition,
                nosePosition = nosePosition
            )
        }

        //If the game has already started and player is not on screen, the player lose the game
        if (
            state.gameState == GameState.OnGame &&
            rightIndexPosition == PointF(400f, 400f) &&
            rightElbowPosition == PointF(800f, 800f) &&
            rightShoulderPosition == PointF(0f, 0f) &&
            leftIndexPosition == PointF(400f, 400f) &&
            leftElbowPosition == PointF(800f, 800f) &&
            leftShoulderPosition == PointF(0f, 0f) &&
            nosePosition == PointF(0f, 0f)
        ) {
            viewModel.loseGame()
        }

        //Detects player arm movements to make the character move
        viewModel.runningCollisionDetection(
            rightIndexPosition = rightIndexPosition,
            rightElbowPosition = rightElbowPosition,
            rightShoulderPosition = rightShoulderPosition,
            leftIndexPosition = leftIndexPosition,
            leftElbowPosition = leftElbowPosition,
            leftShoulderPosition = leftShoulderPosition
        )

        LaunchedEffect(
            state.isRightShoulderCollision,
            state.isLeftShoulderCollision,
            state.isRightElbowCollision,
            state.isLeftElbowCollision,
            state.gameState
        ) {
            if (state.isRightShoulderCollision && state.isLeftShoulderCollision &&
                state.isRightElbowCollision && state.isLeftElbowCollision &&
                state.gameState == GameState.OnGame
            ) {
                viewModel.addSteps()
                viewModel.setCollisionToFalse()
            }
        }

        //Manage the game sound emission
        LaunchedEffect(state.gameState, state.dollState) {
            if (state.dollState == DollState.Singing && state.gameState == GameState.OnGame) {
                viewModel.playSingingDollSound()
                val dollSingingTime = viewModel.getPlaybackMilliSeconds(R.raw.doll_singing_audio)
                delay(dollSingingTime)
                viewModel.setDollState(DollState.Staring)
                viewModel.registerPlayerFreezePose(
                    rightIndexFreezePosition = rightIndexPosition,
                    rightElbowFreezePosition = rightElbowPosition,
                    rightShoulderPosition = rightShoulderPosition,
                    leftIndexFreezePosition = leftIndexPosition,
                    leftElbowFreezePosition = leftElbowPosition,
                    leftShoulderPosition = leftShoulderPosition,
                    noseFreezePosition = nosePosition
                )
            }

            if (state.dollState == DollState.Shooting) {
                viewModel.playDollShootingSound()
            }

            if (state.gameState == GameState.WinGame) {
                viewModel.playWinningGameSound()
                viewModel.verifyIfGameIsInTopFive()
            }

            if (state.dollState == DollState.Staring && state.gameState == GameState.OnGame) {
                val randomStaringTime = (6000L..10000L).random()
                delay(randomStaringTime)
                viewModel.setDollState(DollState.Singing)
            }
        }

        //Monitors player movement while movement is not allowed
        if (state.dollState == DollState.Staring && state.gameState == GameState.OnGame) {
            viewModel.identifyPlayerFreezeMovement(
                rightIndexPosition = rightIndexPosition,
                rightElbowPosition = rightElbowPosition,
                rightShoulderPosition = rightShoulderPosition,
                leftIndexPosition = leftIndexPosition,
                leftElbowPosition = leftElbowPosition,
                leftShoulderPosition = leftShoulderPosition,
                nosePosition = nosePosition
            )
        }

        //Open the losing dialog when the player loses the game
        if (state.dollState == DollState.Shooting && state.gameState == GameState.LoseGame) {
            LosingDialog(
                onRestartGame = { viewModel.initializeGame() },
                onBackToHomeScreen = {
                    viewModel.setGameState(GameState.GamePaused)
                    viewModel.setDollState(DollState.Staring)
                    onBackToHomeScreen()
                }
            )
        }

        //Open the winning dialog when the player wins the game
        if (steps == totalSteps) {
            viewModel.setGameState(GameState.WinGame)
            WinningDialog(
                gameTime = state.gameTimer,
                onRestartGame = { viewModel.initializeGame() },
                onBackToHomeScreen = {
                    viewModel.setStepsToZero()
                    onBackToHomeScreen()
                }
            )
        }

        //Open the high score dialog when the player completes the game in the top five fastest time
        if (state.isShowTopPlayerDialog) {
            HighScoreDialog(
                userName = state.gameUserName,
                highScorePosition = state.topFivePlayerPosition,
                onUserNameChanger = { viewModel.onHighScoreUserNameTextChanged(it) },
                onRegisterGameRecord = {
                    if (state.gameUserName.isNotEmpty()) {
                        viewModel.insertGameInTopFiveTable()
                    } else {
                        viewModel.showEmptyUserNameError()
                    }
                },
                isUserNameEmptyError = state.isShowUserNameError
            )
        }

        GameCharactersComposable(state, offsetX)
    }
}

@Composable
private fun GameCharactersComposable(
    state: CameraUiState,
    offsetX: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        when (state.gameState) {
            GameState.WinGame -> {
                Image(
                    painter = painterResource(id = R.drawable.player_celebrating),
                    contentDescription = "player running image",
                    modifier = Modifier
                        .height(200.dp)
                        .offset(x = (-50).dp)
                        .graphicsLayer {
                            translationX = offsetX.value
                        }
                        .align(Alignment.BottomStart)
                )
            }

            GameState.LoseGame -> {
                Image(
                    painter = painterResource(id = R.drawable.player_down),
                    contentDescription = "player running image",
                    modifier = Modifier
                        .height(200.dp)
                        .offset(
                            x = (-50).dp,
                            y = 50.dp
                        )
                        .graphicsLayer {
                            translationX = offsetX.value
                        }
                        .align(Alignment.BottomStart)
                )
            }

            else -> {
                Image(
                    painter = painterResource(id = R.drawable.player_running),
                    contentDescription = "player running image",
                    modifier = Modifier
                        .height(200.dp)
                        .offset(x = (-50).dp)
                        .graphicsLayer {
                            translationX = offsetX.value
                        }
                        .align(Alignment.BottomStart)
                )
            }
        }

        when (state.dollState) {
            DollState.Shooting -> {
                Image(
                    painter = painterResource(id = R.drawable.doll_shooting),
                    contentDescription = "doll shooting image",
                    modifier = Modifier
                        .height(200.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            DollState.Singing -> {
                Image(
                    painter = painterResource(id = R.drawable.doll_eyes_closed2),
                    contentDescription = "doll eyes closed image",
                    modifier = Modifier
                        .height(200.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            DollState.Staring -> {
                Image(
                    painter = painterResource(id = R.drawable.doll_facing_left),
                    contentDescription = "doll staring image",
                    modifier = Modifier
                        .height(200.dp)
                        .align(Alignment.BottomEnd)
                )
            }
        }
    }
}

@Composable
private fun StartGameTimerCountDown(
    state: CameraUiState,
    onSwitchCheckedChange: (Boolean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.9f)),
    ) {
        PoseDetectionVisibilitySwitch(
            state = state,
            onSwitchCheckedChange = onSwitchCheckedChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .offset(y = 100.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = state.startGameTimer.toString(),
                color = PinkSquish,
                fontSize = 220.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
private fun StartGameButton(
    state: CameraUiState,
    onStartGame: () -> Unit = {},
    onSwitchCheckedChange: (Boolean) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.9f))
    ) {
        PoseDetectionVisibilitySwitch(
            state = state,
            onSwitchCheckedChange = onSwitchCheckedChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .offset(y = 100.dp)
        )
        Button(
            onClick = {
                onStartGame()
            },
            colors = ButtonDefaults.buttonColors(containerColor = PinkSquish),
            shape = RectangleShape,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Click to Start", fontSize = 36.sp)
        }
    }
}

@Composable
private fun PoseDetectionVisibilitySwitch(
    state: CameraUiState,
    onSwitchCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Show body detection points", color = Color.White, fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Switch(
            checked = state.isShowPoseDetectionPoints,
            onCheckedChange = {
                onSwitchCheckedChange(it)

            },
            thumbContent = if (state.isShowPoseDetectionPoints) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}


@Composable
private fun GameTimer(state: CameraUiState) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.officer_triangle),
                contentDescription = "officer triangle image",
                modifier = Modifier
                    .padding(top = 40.dp, end = 16.dp)
                    .height(70.dp)
                    .fillMaxWidth(),
                alignment = Alignment.BottomEnd
            )
            Image(
                painter = painterResource(R.drawable.officer_square),
                contentDescription = "officer triangle image",
                modifier = Modifier
                    .padding(top = 40.dp, start = 16.dp)
                    .height(70.dp)
                    .fillMaxWidth(),
                alignment = Alignment.BottomStart
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 40.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Timer,
                    contentDescription = "timer icon",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = state.gameTimer.toTimeString(),
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight(600)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameTimerPreview() {
    GameTimer(state = CameraUiState())
}

@Preview(showBackground = true)
@Composable
private fun StartGameButtonPreview() {
    StartGameButton(state = CameraUiState())
}

@Preview(showBackground = true)
@Composable
private fun StartGameTimerCountDownPreview() {
    StartGameTimerCountDown(state = CameraUiState())
}
