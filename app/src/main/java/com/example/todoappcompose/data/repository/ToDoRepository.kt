package com.example.todoappcompose.data.repository

import com.example.todoappcompose.data.ToDoDao
import com.example.todoappcompose.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Todo Repository
 */
@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {

    val getAllTasks:  Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority:  Flow<List<ToDoTask>> = toDoDao.sortByLowPriority()
    val sortByHighPriority:  Flow<List<ToDoTask>> = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId = taskId)
    }

    suspend fun addTask(toDoTask: ToDoTask) {
         toDoDao.addTask(toDoTask = toDoTask)
    }

    suspend fun updatetask(toDoTask: ToDoTask) {
         toDoDao.updateTask(toDoTask = toDoTask)
    }

 /*   suspend fun deleteTask(toDoTask: ToDoTask) {
         toDoDao.deleteTask(toDoTask = toDoTask)
    }

    suspend fun deleteAllTasks(toDoTask: ToDoTask) {
        toDoDao.deleteAllTasks()
    }*/

    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>> {
        return toDoDao.searchDatabase(searchQuery = searchQuery)
    }
}