package br.com.steventung.squishgame.ui.screens.home

import br.com.steventung.squishgame.domain.model.Game

data class HomeUiState(
    val fastestPlayerName: String = "",
    val fastestGameTime: Long? = null,
    val isShowTopFiveDialog: Boolean = false,
    val topFivePlayersList: List<Game?> = emptyList()
)
