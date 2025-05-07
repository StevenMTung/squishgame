package br.com.steventung.squishgame.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.steventung.squishgame.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFastestPlayerInfo()
        loadTopFivePlayersList()
    }

    private fun loadFastestPlayerInfo() {
        viewModelScope.launch {
            gameRepository.getFastestPlayer().collect { fastestPlayerGame ->
                fastestPlayerGame?.let {
                    _uiState.update { currentState ->
                        currentState.copy(
                            fastestPlayerName = it.gameUserName,
                            fastestGameTime = it.scoreTime
                        )
                    }
                }
            }
        }
    }

    private fun loadTopFivePlayersList() {
        viewModelScope.launch {
            gameRepository.getGameListAscendingByScoreTime().collect { topFiveGameList ->
                _uiState.update { it.copy(topFivePlayersList = topFiveGameList) }
            }
        }
    }

    fun setTopFivePlayersVisibility(visibility: Boolean) {
        _uiState.update { it.copy(isShowTopFiveDialog = visibility) }
    }

}