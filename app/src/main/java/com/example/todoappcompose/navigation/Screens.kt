package com.example.todoappcompose.navigation

import androidx.navigation.NavHostController
import com.example.todoappcompose.utils.Action
import com.example.todoappcompose.utils.Constants.LIST_SCREEN
import com.example.todoappcompose.utils.Constants.SPLASH_SCREEN

/***
 * Screens navigation
 */
class Screens (navController: NavHostController) {

    val splash : () -> Unit = {
        navController.navigate(route = "list/${Action.NO_ACTION.name}") {
            popUpTo(SPLASH_SCREEN) { inclusive = true }
        }
    }

    val list : (Int) -> Unit = { taskId ->
        navController.navigate(route ="task/$taskId")
    }

    val task :(Action) -> Unit = { action ->
        navController.navigate("list/${action.name}") {
            popUpTo(LIST_SCREEN){ inclusive = true }
        }
    }
}