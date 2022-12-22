package com.example.todoappcompose.navigation.destination


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoappcompose.utils.Constants.LIST_ARGUMENT_KEY
import com.example.todoappcompose.utils.Constants.LIST_SCREEN

/**
 * List composable
 */
fun NavGraphBuilder.listComposable(navigateToTaskScreen : (Int) -> Unit) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ){

    }
}