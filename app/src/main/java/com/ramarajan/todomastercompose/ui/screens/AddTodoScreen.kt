package com.ramarajan.todomastercompose.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.ui.viewmodel.TodoViewModel
import com.ramarajan.todomastercompose.ui.widgets.CustomToggleWidget


@Composable
fun AddTodoScreen(
    todoViewModel: TodoViewModel = hiltViewModel(),
    todoId: Int?, onAddItem: (String, String) -> Unit, onToggleCompleted: (TodoEntity) -> Unit) {
    val todo by todoViewModel.todoItemData.collectAsState()
    todoViewModel.getTodoById(1)
    println("AddScreen "+todo?.todo)

}

@Composable
fun AddToDoSection(todoEntity: TodoEntity?,onAddClicked: (String, Boolean) -> Unit) {
    println("Todos"+todoEntity)
    var title by remember { mutableStateOf(todoEntity?.todo) }
    var status by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        )  {
            CustomToggleWidget(onToggle = {
                status = it
            })
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically

            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Outlined.Close,contentDescription = "close")
                }

                IconButton(onClick = {
                    title?.let { onAddClicked.invoke(it,status) }
                }) {
                    Icon(Icons.Outlined.Check,contentDescription = "close")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        title?.let {
            TextField(
                value = it,
                maxLines = 16,
                onValueChange = { title = it },
                placeholder = { Text("Enter your todo here..") },
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            )
        }

    }
}

// To-Do card
@Composable
fun ToDoCard(item: TodoEntity, onToggleCompleted: (TodoEntity) -> Unit) {
    Surface(
        color = if (item.completed == true) Color.LightGray else MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onToggleCompleted(item) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Checkbox(checked = item.completed == true, onCheckedChange = { onToggleCompleted(item.copy(completed = it)) })
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                item.todo?.let { Text(it, style = MaterialTheme.typography.headlineLarge) }
//                if (item.description.isNotEmpty()) {
//                    Text(item.description)
//                }
            }
        }
    }
}