package com.ramarajan.todomastercompose.data.local.service

import com.ramarajan.todomastercompose.data.local.db.TodoDatabase
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoDatabaseService(
    private val todoDatabase: TodoDatabase
) : DatabaseService {
    override fun getAllTodos(): Flow<List<TodoEntity>> {
        return todoDatabase.getTodoDao().getAllTodo()
    }

    override fun getTodoById(todoId : Int): TodoEntity? {
        return todoDatabase.getTodoDao().getTodoById(todoId)
    }

    override fun deleteAllAndInsertAll(todos: List<TodoEntity>) {
       return todoDatabase.getTodoDao().deleteAllAndInsertAll(todos)
    }
}