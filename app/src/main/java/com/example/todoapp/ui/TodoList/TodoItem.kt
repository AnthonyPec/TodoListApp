package com.example.todoapp.ui.TodoList

import android.text.Layout
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.data.Todo


@Composable
fun TodoItem(
    todo: Todo,
    onEvent: (TodoListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
//        modifier = Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = todo.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            IconButton(onClick = {
                onEvent(TodoListEvent.OnDeleteTodo(todo))
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }

        }

        todo.description?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it
            )
        }

        Checkbox(
            checked = todo.isDone,
        onCheckedChange = {
            isChecked->
            onEvent(TodoListEvent.OnDoneChange(todo, isChecked))
        })

    }

}