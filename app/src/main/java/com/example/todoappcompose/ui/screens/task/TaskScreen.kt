package com.example.todoappcompose.ui.screens.task

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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


    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                navigateToListScreen = navigateToListScreen)
        },
        content = {
            TaskContent(
                title = title,
                onTitleChange = { shareViewModel.title.value =  it },
                description = description ,
                onDescriptionChange = { shareViewModel.description.value =  it },
                priority = priority,
                onPrioritySelected = { shareViewModel.priority.value =  it  }
            )
        }
    )
}