package br.com.steventung.squishgame.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.steventung.squishgame.ui.screens.camera.CameraContainerScreen

fun NavGraphBuilder.cameraContainerGraph(
    onBackToHomeScreen: () -> Unit = {},
) {
    composable(route = AppDestination.CameraContainer.route) {
        CameraContainerScreen(
            onBackToHomeScreen = onBackToHomeScreen
        )
    }
}