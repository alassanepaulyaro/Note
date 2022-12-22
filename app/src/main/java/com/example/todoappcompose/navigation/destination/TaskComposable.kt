package com.example.todoappcompose.navigation.destination

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoappcompose.utils.Action
import com.example.todoappcompose.utils.Constants.TASK_ARGUMENT_KEY
import com.example.todoappcompose.utils.Constants.TASK_SCREEN

/**
 * Task composable
 */
fun NavGraphBuilder.taskComposable(navigateToListScreen : (Action) -> Unit) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ){

    }
}