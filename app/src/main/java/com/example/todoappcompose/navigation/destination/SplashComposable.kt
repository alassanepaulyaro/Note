package com.example.todoappcompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.todoappcompose.ui.screens.splash.SplashScreen
import com.example.todoappcompose.utils.Constants.SPLASH_SCREEN

/**
 * Splash Screen composable
 */
fun NavGraphBuilder.splashComposable(
    navigateToListScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        ) {
        SplashScreen(navigateToListScreen = navigateToListScreen)
    }
}