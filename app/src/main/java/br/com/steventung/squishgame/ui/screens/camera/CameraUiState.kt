package br.com.steventung.squishgame.ui.screens.camera

import android.graphics.PointF

const val GAME_TIMER = 90_000L
const val START_GAME_TIMER = 3


data class CameraUiState(
    val imageSize: Pair<Float, Float> = Pair(480f, 640f),
    val previewSize: Pair<Float, Float> = Pair(0f, 0f),
    val postScaleWidthOffset: Float = 0f,
    val imageAspectRatio: Float = 0f,
    val scaleFactor: Float = 0f,
    val gameState: GameState = GameState.GamePaused,
    val isRightShoulderCollision: Boolean = false,
    val isRightElbowCollision: Boolean = false,
    val isLeftShoulderCollision: Boolean = false,
    val isLeftElbowCollision: Boolean = false,
    val gameTimer: Long = GAME_TIMER,
    val startGameTimer: Int = START_GAME_TIMER,
    val steps: Int = 0,
    val dollState: DollState = DollState.Staring,
    val rightIndexFreezePosition: PointF = PointF(0f, 0f),
    val rightElbowFreezePosition: PointF = PointF(0f, 0f),
    val rightShoulderFreezePosition: PointF = PointF(0f, 0f),
    val leftIndexFreezePosition: PointF = PointF(0f, 0f),
    val leftElbowFreezePosition: PointF = PointF(0f, 0f),
    val leftShoulderFreezePosition: PointF = PointF(0f, 0f),
    val noseFreezePosition: PointF = PointF(0f, 0f),
    val gameUserName: String = "",
    val isShowUserNameError: Boolean = false,
    val isShowTopPlayerDialog: Boolean = false,
    val topFivePlayerPosition: Int? = null,
    val isShowPoseDetectionPoints: Boolean = false
)

sealed class GameState {
    data object GamePaused: GameState()
    data object GameCountDown: GameState()
    data object OnGame: GameState()
    data object WinGame: GameState()
    data object LoseGame: GameState()
}

sealed class DollState {
    data object Singing: DollState()
    data object Staring: DollState()
    data object Shooting: DollState()
}

