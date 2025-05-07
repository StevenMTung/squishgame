package br.com.steventung.squishgame.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.steventung.squishgame.ui.screens.instructions.InstructionsScreen

fun NavGraphBuilder.gameInstructionsGraph(
    onBackNavigation: () -> Unit = {}
) {
    composable(route = AppDestination.GameInstructions.route) {
        InstructionsScreen(
            onBackNavigation = onBackNavigation
        )
    }
}