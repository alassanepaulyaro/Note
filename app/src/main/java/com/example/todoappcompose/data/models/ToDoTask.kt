package com.example.todoappcompose.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoappcompose.utils.Constants.DATABASE_TABLE

/**
 * Task Data
 */
@Entity(tableName = DATABASE_TABLE)
data class ToDoTask (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title : String,
    val description: String,
    val priority: Priority
)
