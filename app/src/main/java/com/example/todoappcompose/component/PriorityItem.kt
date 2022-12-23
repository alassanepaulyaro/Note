package com.example.todoappcompose.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoappcompose.data.models.Priority
import com.example.todoappcompose.ui.theme.LARGE_PADDING
import com.example.todoappcompose.ui.theme.PRIORITY_INDICATOR_SIZE

@Composable
fun PriorityItem(priority: Priority){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(modifier= Modifier.size(PRIORITY_INDICATOR_SIZE)){
            drawCircle(color = priority.color)
        }
        Text(
            modifier =  Modifier.padding(start = LARGE_PADDING),
            text = priority.name,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PriorityItemPreview(){
    PriorityItem(priority = Priority.LOW)
}