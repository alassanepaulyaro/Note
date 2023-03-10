package com.example.todoappcompose.navigation.destination


import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todoappcompose.data.viewmodel.ShareViewModel
import com.example.todoappcompose.ui.screens.list.ListScreen
import com.example.todoappcompose.utils.Constants.LIST_ARGUMENT_KEY
import com.example.todoappcompose.utils.Constants.LIST_SCREEN
import com.example.todoappcompose.utils.toAction

/**
 * List composable
 */
@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.listComposable(
    navigateToTaskScreen : (taskId: Int) -> Unit,
    shareViewModel: ShareViewModel
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ){ navBackStackEntry ->
        val action = navBackStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action) {
            shareViewModel.action.value = action
        }

        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            shareViewModel = shareViewModel
        )
    }
}