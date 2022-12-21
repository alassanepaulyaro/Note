package com.example.todoappcompose.data.models

import androidx.compose.ui.graphics.Color
import com.example.todoappcompose.ui.theme.HighPriorityColor
import com.example.todoappcompose.ui.theme.LowPriorityColor
import com.example.todoappcompose.ui.theme.MediumPriorityColor
import com.example.todoappcompose.ui.theme.NonePriorityColor

/***
 * Color
 */
enum class Priority (val color: Color) {
    HIGH(HighPriorityColor),
    MEDIUM(MediumPriorityColor),
    LOW(LowPriorityColor),
    NONE(NonePriorityColor),
}