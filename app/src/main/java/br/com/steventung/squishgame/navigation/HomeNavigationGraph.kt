package br.com.steventung.squishgame.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.steventung.squishgame.ui.screens.home.HomeScreen

fun NavGraphBuilder.homeGraph(
    onNavigateToGameScreen: () -> Unit = {},
    onNavigateToInstructionsScreen: () -> Unit = {}
) {
    composable(route = AppDestination.Home.route) {
        HomeScreen(
            onNavigateToGameScreen = {
                onNavigateToGameScreen()
            },
            onNavigateToInstructionsScreen = {
                onNavigateToInstructionsScreen()
            }
        )
    }
}