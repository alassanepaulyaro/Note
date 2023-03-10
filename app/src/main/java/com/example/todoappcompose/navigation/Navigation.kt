package com.example.todoappcompose.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todoappcompose.data.viewmodel.ShareViewModel
import com.example.todoappcompose.navigation.destination.listComposable
import com.example.todoappcompose.navigation.destination.splashComposable
import com.example.todoappcompose.navigation.destination.taskComposable
import com.example.todoappcompose.utils.Constants.SPLASH_SCREEN

/**
 * Navigation
 */
@ExperimentalMaterialApi
@Composable
fun SetUpNavigation(
    navController: NavHostController,
    shareViewModel: ShareViewModel
) {
    val screen = remember (navController) {
        Screens(navController = navController)
    }
    
    NavHost(
        navController = navController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable (
        navigateToListScreen = screen.splash
        )
        listComposable(
            navigateToTaskScreen = screen.list,
            shareViewModel = shareViewModel
        )
        taskComposable (
            navigateToListScreen = screen.task,
            shareViewModel = shareViewModel
        )
    }
}