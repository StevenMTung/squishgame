package br.com.steventung.squishgame.navigation

sealed class AppDestination(val route: String) {
    data object Home: AppDestination(route = "homeRoute")
    data object CameraContainer: AppDestination(route = "cameraContainerRoute")
    data object GameInstructions: AppDestination(route = "gameInstructionsRoute")
}