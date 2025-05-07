package br.com.steventung.squishgame.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = AppDestination.Home.route
    ) {
        homeGraph(
            onNavigateToGameScreen = {
                navHostController.navigate(AppDestination.CameraContainer.route)
            },
            onNavigateToInstructionsScreen = {
                navHostController.navigate(AppDestination.GameInstructions.route)
            }
        )
        cameraContainerGraph(
            onBackToHomeScreen = {
                navHostController.navigate(AppDestination.Home.route) {
                    popUpTo(AppDestination.Home.route) { inclusive = true }
                }
            }
        )
        gameInstructionsGraph(
            onBackNavigation = {
                navHostController.popBackStack()
            }
        )
    }
}