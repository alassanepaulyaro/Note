package com.example.todoappcompose.ui.screens.task

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.todoappcompose.data.models.Priority
import com.example.todoappcompose.data.models.ToDoTask
import com.example.todoappcompose.data.viewmodel.ShareViewModel
import com.example.todoappcompose.utils.Action

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    selectedTask : ToDoTask?,
    shareViewModel: ShareViewModel,
    navigateToListScreen: (Action) -> Unit
) {
    val title : String by shareViewModel.title
    val description : String by shareViewModel.description
    val priority : Priority by shareViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = { action ->
                    if(action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (shareViewModel.validateField()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context )
                        }
                    }

                })
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = { shareViewModel.updateTitle(it) },
                description = description,
                onDescriptionChange = { shareViewModel.description.value =  it },
                priority = priority,
                onPrioritySelected = { shareViewModel.priority.value =  it  }
            )
        }
    )


}

/**
 * display Toast
 */
fun displayToast(context : Context) {
    Toast.makeText(
        context,
        "Field Empty",
        Toast.LENGTH_LONG
    ).show()
}
