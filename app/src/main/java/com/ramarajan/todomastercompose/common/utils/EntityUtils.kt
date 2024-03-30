package com.ramarajan.todomastercompose.common.utils

import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.data.model.Todo


fun List<Todo?>.apiTodoListToTodoEntityList(): List<TodoEntity> {
    val list = mutableListOf<TodoEntity>()
    forEach { apiTodo ->
        apiTodo?.apiTodoToTodoEntity()?.let { list.add(it) }
    }
    return list
}


fun Todo.apiTodoToTodoEntity(): TodoEntity {
    return TodoEntity(
        todoId = id,
        todo = todo,
        completed = completed,
        userId = userId
    )
}