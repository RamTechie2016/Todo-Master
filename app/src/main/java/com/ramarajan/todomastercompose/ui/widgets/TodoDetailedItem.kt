package com.ramarajan.todomastercompose.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.ui.common.getRandomLightBackgroundColor

@Composable
fun TodoDetailedItem(todoEntity: TodoEntity, onEditClicked: (Int) -> Unit) {
    Card( modifier = Modifier.height(460.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(getRandomLightBackgroundColor()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(

                verticalArrangement = Arrangement.Top
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {}, modifier = Modifier.padding(2.dp)) {
                        Icon(Icons.Outlined.Close, contentDescription = "Close")
                    }
                }
                todoEntity.todo?.let {
                    Text(
                        text = it,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                }

            }
            Column {
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                Row( modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                    Row(

                    ) {
                        if(todoEntity.completed == true) {
                            Card(modifier = Modifier.padding(2.dp)) {
                                Row(modifier = Modifier.padding(4.dp)) {
                                    Icon(Icons.Rounded.CheckCircle, contentDescription = "Close")
                                    Text(text = "Completed")
                                }

                            }

                        }else{

                            Card(modifier = Modifier.padding(2.dp)) {
                                Row(modifier = Modifier.padding(4.dp)) {
                                    Icon(Icons.Outlined.CheckCircle, contentDescription = "Close")
                                    Text(text = "Not Completed")
                                }

                            }

                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = {
                            println("todoEntity :$todoEntity")
                            todoEntity.todoId?.let { onEditClicked.invoke(it) }
                        }, modifier = Modifier.padding(2.dp)) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                modifier = Modifier.padding(6.dp),
                                contentDescription = "status"
                            )
                        }
                        IconButton(onClick = {}, modifier = Modifier.padding(2.dp)) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                modifier = Modifier.padding(6.dp),
                                contentDescription = "status"
                            )
                        }
                    }

                }

            }

        }

    }
}