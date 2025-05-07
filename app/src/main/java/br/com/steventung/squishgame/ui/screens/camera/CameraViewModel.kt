package br.com.steventung.squishgame.ui.screens.camera

import android.graphics.PointF
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.steventung.squishgame.R
import br.com.steventung.squishgame.data.repository.GameRepository
import br.com.steventung.squishgame.domain.model.Game
import br.com.steventung.squishgame.utils.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val soundManager: SoundManager,
    private val gameRepository: GameRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CameraUiState())
    val uiState = _uiState.asStateFlow()

    init {
        soundManager.apply {
            loadSound(
                listOf(
                    R.raw.doll_singing_audio,
                    R.raw.gun_shot,
                    R.raw.winning_sound
                )
            )
        }
    }

    fun releaseSoundManager() {
        soundManager.release()
    }

    fun getPlaybackMilliSeconds(resId: Int): Long {
        return soundManager.getCurrentPlaybackMilliSeconds(resId)
    }

    fun playSingingDollSound() {
        soundManager.playSound(R.raw.doll_singing_audio)
    }

    fun playDollShootingSound() {
        soundManager.playSound(R.raw.gun_shot)
    }

    fun playWinningGameSound() {
        soundManager.playSound(R.raw.winning_sound)
    }

    fun setPreviewSize(value: Pair<Int, Int>) {
        _uiState.value = _uiState.value.copy(
            previewSize = Pair(
                first = value.first.toFloat(),
                second = value.second.toFloat()
            )
        )
        redefineScale()
    }

    fun setScreenSize(value: Pair<Int, Int>) {
        _uiState.value = _uiState.value.copy(
            imageSize = Pair(
                first = value.first.toFloat(),
                second = value.second.toFloat()
            )
        )
        redefineScale()
    }

    private fun redefineScale() {
        with(uiState.value) {
            val scaleFactor: Float = previewSize.second / imageSize.second
            val imageAspectRatio: Float = imageSize.first / imageSize.second
            _uiState.value = _uiState.value.copy(
                scaleFactor = scaleFactor,
                imageAspectRatio = imageAspectRatio,
                postScaleWidthOffset = (previewSize.second * imageAspectRatio - previewSize.first) / 2
            )
        }
    }

    fun runningCollisionDetection(
        rightIndexPosition: PointF,
        rightElbowPosition: PointF,
        rightShoulderPosition: PointF,
        leftIndexPosition: PointF,
        leftElbowPosition: PointF,
        leftShoulderPosition: PointF,
        threshold: Float = 50f
    ) {
        val rightIndexAndElbowDx = rightIndexPosition.x - rightElbowPosition.x
        val rightIndexAndElbowDy = rightIndexPosition.y - rightElbowPosition.y
        val distanceRightIndexAndElbow =
            sqrt(rightIndexAndElbowDx * rightIndexAndElbowDx + rightIndexAndElbowDy * rightIndexAndElbowDy)
        if (distanceRightIndexAndElbow <= threshold) {
            _uiState.update { it.copy(isRightElbowCollision = true) }
        }

        val rightIndexAndShoulderDx = rightIndexPosition.x - rightShoulderPosition.x
        val rightIndexAndShoulderDy = rightIndexPosition.y - rightShoulderPosition.y
        val distanceRightIndexAndShoulder =
            sqrt(rightIndexAndShoulderDx * rightIndexAndShoulderDx + rightIndexAndShoulderDy * rightIndexAndShoulderDy)
        if (distanceRightIndexAndShoulder <= threshold) {
            _uiState.update { it.copy(isRightShoulderCollision = true) }
        }

        val leftIndexAndElbowDx = leftIndexPosition.x - leftElbowPosition.x
        val leftIndexAndElbowDy = leftIndexPosition.y - leftElbowPosition.y
        val distanceLeftIndexAndElbow =
            sqrt(leftIndexAndElbowDx * leftIndexAndElbowDx + leftIndexAndElbowDy * leftIndexAndElbowDy)
        if (distanceLeftIndexAndElbow <= threshold) {
            _uiState.update { it.copy(isLeftElbowCollision = true) }
        }

        val leftIndexAndShoulderDx = leftIndexPosition.x - leftShoulderPosition.x
        val leftIndexAndShoulderDy = leftIndexPosition.y - leftShoulderPosition.y
        val distanceLeftIndexAndShoulder =
            sqrt(leftIndexAndShoulderDx * leftIndexAndShoulderDx + leftIndexAndShoulderDy * leftIndexAndShoulderDy)
        if (distanceLeftIndexAndShoulder <= threshold) {
            _uiState.update { it.copy(isLeftShoulderCollision = true) }
        }
    }

    fun setCollisionToFalse() {
        _uiState.update {
            it.copy(
                isRightShoulderCollision = false,
                isLeftShoulderCollision = false,
                isRightElbowCollision = false,
                isLeftElbowCollision = false
            )
        }
    }

    fun initializeGame() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    gameState = GameState.GameCountDown,
                    gameTimer = GAME_TIMER,
                    startGameTimer = START_GAME_TIMER,
                    steps = 0,
                    dollState = DollState.Staring
                )
            }
            while (_uiState.value.startGameTimer > 0) {
                delay(1000)
                _uiState.update { it.copy(startGameTimer = _uiState.value.startGameTimer - 1) }
            }
            _uiState.update {
                it.copy(
                    gameState = GameState.OnGame,
                    dollState = DollState.Singing
                )
            }
        }
    }

    fun startGameTimerCountDown() {
        viewModelScope.launch {
            var lastTimestamp = System.currentTimeMillis()
            while (_uiState.value.gameTimer > 0L && _uiState.value.gameState == GameState.OnGame) {
                delay(10L)
                _uiState.update {
                    it.copy(
                        gameTimer = (_uiState.value.gameTimer - (System.currentTimeMillis() - lastTimestamp))
                            .coerceAtLeast(0L)
                    )
                }
                lastTimestamp = System.currentTimeMillis()
            }
        }
    }

    fun setGameState(gameState: GameState) {
        _uiState.update { it.copy(gameState = gameState) }
    }

    fun setDollState(dollState: DollState) {
        _uiState.update { it.copy(dollState = dollState) }
    }

    fun addSteps() {
        _uiState.update { it.copy(steps = _uiState.value.steps + 1) }
    }

    fun setStepsToZero() {
        _uiState.update { it.copy(steps = 0) }
    }

    fun registerPlayerFreezePose(
        rightIndexFreezePosition: PointF,
        rightElbowFreezePosition: PointF,
        rightShoulderPosition: PointF,
        leftIndexFreezePosition: PointF,
        leftElbowFreezePosition: PointF,
        leftShoulderPosition: PointF,
        noseFreezePosition: PointF,
    ) {
        _uiState.update {
            it.copy(
                rightIndexFreezePosition = rightIndexFreezePosition,
                rightElbowFreezePosition = rightElbowFreezePosition,
                rightShoulderFreezePosition = rightShoulderPosition,
                leftIndexFreezePosition = leftIndexFreezePosition,
                leftElbowFreezePosition = leftElbowFreezePosition,
                leftShoulderFreezePosition = leftShoulderPosition,
                noseFreezePosition = noseFreezePosition
            )
        }
    }

    fun identifyPlayerFreezeMovement(
        rightIndexPosition: PointF,
        rightElbowPosition: PointF,
        rightShoulderPosition: PointF,
        leftIndexPosition: PointF,
        leftElbowPosition: PointF,
        leftShoulderPosition: PointF,
        nosePosition: PointF,
        threshold: Float = 20f
    ) {
        val rightIndexDx = rightIndexPosition.x - _uiState.value.rightIndexFreezePosition.x
        val rightIndexDy = rightIndexPosition.y - _uiState.value.rightIndexFreezePosition.y
        val distanceRightIndex = sqrt(rightIndexDx * rightIndexDx + rightIndexDy * rightIndexDy)
        if (distanceRightIndex >= threshold) {
            loseGame()
        }

        val rightElbowDx = rightElbowPosition.x - _uiState.value.rightElbowFreezePosition.x
        val rightElbowDy = rightElbowPosition.y - _uiState.value.rightElbowFreezePosition.y
        val distanceRightElbow = sqrt(rightElbowDx * rightElbowDx + rightElbowDy * rightElbowDy)
        if (distanceRightElbow >= threshold) {
            loseGame()
        }

        val rightShoulderDx = rightShoulderPosition.x - _uiState.value.rightShoulderFreezePosition.x
        val rightShoulderDy = rightShoulderPosition.y - _uiState.value.rightShoulderFreezePosition.y
        val distanceRightShoulder =
            sqrt(rightShoulderDx * rightShoulderDx + rightShoulderDy * rightShoulderDy)
        if (distanceRightShoulder >= threshold) {
            loseGame()
        }

        val leftIndexDx = leftIndexPosition.x - _uiState.value.leftIndexFreezePosition.x
        val leftIndexDy = leftIndexPosition.y - _uiState.value.leftIndexFreezePosition.y
        val distanceLeftIndex = sqrt(leftIndexDx * leftIndexDx + leftIndexDy * leftIndexDy)
        if (distanceLeftIndex >= threshold) {
            loseGame()
        }

        val leftElbowDx = leftElbowPosition.x - _uiState.value.leftElbowFreezePosition.x
        val leftElbowDy = leftElbowPosition.y - _uiState.value.leftElbowFreezePosition.y
        val distanceLeftElbow = sqrt(leftElbowDx * leftElbowDx + leftElbowDy * leftElbowDy)
        if (distanceLeftElbow >= threshold) {
            loseGame()
        }

        val leftShoulderDx = leftShoulderPosition.x - _uiState.value.leftShoulderFreezePosition.x
        val leftShoulderDy = leftShoulderPosition.y - _uiState.value.leftShoulderFreezePosition.y
        val distanceLeftShoulder =
            sqrt(leftShoulderDx * leftShoulderDx + leftShoulderDy * leftShoulderDy)
        if (distanceLeftShoulder >= threshold) {
            loseGame()
        }

        val noseDx = nosePosition.x - _uiState.value.noseFreezePosition.x
        val noseDy = nosePosition.y - _uiState.value.noseFreezePosition.y
        val distanceNose = sqrt(noseDx * noseDx + noseDy * noseDy)
        if (distanceNose >= threshold) {
            loseGame()
        }
    }

    fun loseGame() {
        _uiState.update {
            it.copy(
                gameState = GameState.LoseGame,
                dollState = DollState.Shooting
            )
        }
    }

    fun verifyIfGameIsInTopFive() {
        viewModelScope.launch {
            val gameList = gameRepository.getGameListAscendingByScoreTime().first()
            val gameTimeList = gameList.map { it?.scoreTime }.toMutableList()
            val currentTimeScore = GAME_TIMER - _uiState.value.gameTimer
            val isTopPlayer = if (gameList.size == 5) {
                gameList.last()?.scoreTime?.let { it > currentTimeScore } ?: false
            } else true

            if (isTopPlayer) {
                gameTimeList.add(currentTimeScore)
                val gameListOrderByAscending = gameTimeList.sortedBy { it }
                val index = gameListOrderByAscending.indexOfFirst { it == currentTimeScore }
                _uiState.update {
                    it.copy(
                        isShowTopPlayerDialog = true,
                        topFivePlayerPosition = index + 1
                    )
                }
            }
        }
    }

    fun insertGameInTopFiveTable() {
        viewModelScope.launch {
            createGame()
            val gameList = gameRepository.getGameListAscendingByScoreTime().first()
            if (gameList.isNotEmpty() && gameList.size > 5) {
                gameList.last()?.let { lastGame ->
                    deleteLastGame(lastGame.id)
                }
            }
            _uiState.update {
                it.copy(
                    isShowTopPlayerDialog = false,
                    gameUserName = ""
                )
            }
        }
    }

    private suspend fun createGame() {
        val game = Game(
            gameUserName = _uiState.value.gameUserName,
            scoreTime = GAME_TIMER - _uiState.value.gameTimer
        )
        gameRepository.createGame(game = game)
    }

    private suspend fun deleteLastGame(id: String) {
        gameRepository.removeGame(id)
    }

    fun onHighScoreUserNameTextChanged(userNameText: String) {
        _uiState.update { it.copy(gameUserName = userNameText) }
    }

    fun showEmptyUserNameError() {
        viewModelScope.launch {
            _uiState.update { it.copy(isShowUserNameError = true) }
            delay(3000)
            _uiState.update { it.copy(isShowUserNameError = false) }
        }
    }

    fun setPoseDetectionPointVisibility(show: Boolean) {
        _uiState.update { it.copy(isShowPoseDetectionPoints = show) }
    }

}