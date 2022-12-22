package com.example.todoappcompose.navigation

import androidx.navigation.NavHostController
import com.example.todoappcompose.utils.Action
import com.example.todoappcompose.utils.Constants.LIST_SCREEN

/***
 * Screens navigation
 */
class Screens (navController: NavHostController) {

    val list:(Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN){ inclusive = true }
        }
    }

    val task: (Int) -> Unit = { taskId ->
        navController.navigate(route ="task/$taskId")
    }
}