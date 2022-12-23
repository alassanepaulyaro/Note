package com.example.todoappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todoappcompose.data.viewmodel.ShareViewModel
import com.example.todoappcompose.navigation.destination.listComposable
import com.example.todoappcompose.navigation.destination.taskComposable
import com.example.todoappcompose.utils.Constants.LIST_SCREEN

/**
 * Navigation
 */
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
        startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screen.task,
            shareViewModel = shareViewModel
        )
        taskComposable ( navigateToListScreen = screen.list )
    }
}