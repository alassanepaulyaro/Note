package com.example.todoappcompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoappcompose.data.models.ToDoTask

@Database(entities = [ToDoTask::class], version = 1, exportSchema = false)
abstract class ToDoDataBase : RoomDatabase() {
    abstract fun toDoDao(): ToDoDao
}