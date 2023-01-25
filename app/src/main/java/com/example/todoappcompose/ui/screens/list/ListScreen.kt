package com.example.todoappcompose.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.todoappcompose.R
import com.example.todoappcompose.data.viewmodel.ShareViewModel
import com.example.todoappcompose.ui.theme.fabBackgroundColor
import com.example.todoappcompose.utils.Action
import com.example.todoappcompose.utils.SearchAppBarState
import kotlinx.coroutines.launch

/**
 * List Screen
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen : (taskId: Int) -> Unit,
    shareViewModel: ShareViewModel
) {
    LaunchedEffect(key1 = true) {
        shareViewModel.getAllTasks()
        shareViewModel.readSortState()
    }

    val action by shareViewModel.action

    val allTasks by shareViewModel.allTasks.collectAsState()
    val searchedTasks by shareViewModel.searchedTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by shareViewModel.searchAppBarState
    val searchTextSate: String by shareViewModel.searchTextState
    val sortState by shareViewModel.sortState.collectAsState()
    val lowPriorityTasks by shareViewModel.lowPriorityTask.collectAsState()
    val highPriorityTasks by shareViewModel.highPriorityTask.collectAsState()

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { shareViewModel.handleDatabaseActions(action = action) },
        onUndoClicked = {
            shareViewModel.action.value = it
        },
        taskTitle = shareViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState =  scaffoldState,
        topBar = {
            ListAppBar(
                shareViewModel = shareViewModel,
                searchAppBarState = searchAppBarState,
                searchTextSate = searchTextSate
            )
        },
        content = {
            ListContent(
                allTasks = allTasks,
                searchedTasks =  searchedTasks,
                searchAppBarState = searchAppBarState,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                onSwipeToDelete = { action, task  ->
                    shareViewModel.action.value = action
                    shareViewModel.updateTaskFields(selectedTask = task)
                },
                navigateToTaskScreen = navigateToTaskScreen
            )
        },
        floatingActionButton = {
            ListFab(onFabClicked = navigateToTaskScreen)
        })
}

/**
 * List Fab
 */
@Composable
fun ListFab (
    onFabClicked : (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

/**
 * Display Snack Bar
 */
@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoClicked : (Action) -> Unit,
    taskTitle : String,
    action : Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(
                        action = action,
                        taskTitle = taskTitle),
                    actionLabel = setActionLabel(action= action)
                )
                undoDeleteTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

/**
 *  set Message snack bar
 */
private fun setMessage(
    action: Action,
    taskTitle: String
) : String {
    return when(action) {
        Action.DELETE_ALL -> "All Tasks removed"
        else -> "${action.name} : $taskTitle"
    }
}

/**
 * set Action Label
 */
private fun setActionLabel(action: Action) :String {
    return if (action.name ==  "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}

/**
 * undo Delete Task
 */
private fun undoDeleteTask (
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked : (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed
        && action ==  Action.DELETE) {
        onUndoClicked(Action.UNDO)
    }
}