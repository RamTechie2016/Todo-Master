package com.ramarajan.todomastercompose.data.repository

import com.ramarajan.todomastercompose.common.utils.Constants.DEFAULT_PAGE_NUM
import com.ramarajan.todomastercompose.common.utils.apiTodoListToTodoEntityList
import com.ramarajan.todomastercompose.common.utils.apiTodoToTodoEntity
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import com.ramarajan.todomastercompose.data.local.service.DatabaseService
import com.ramarajan.todomastercompose.data.network.TodoApiService
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepository @Inject constructor(
    private val database: DatabaseService,
    private val network: TodoApiService
) {
    suspend fun getAllTodo(pageNumber: Int = DEFAULT_PAGE_NUM) : List<TodoEntity>? {

        val todos = network.getAllTodo(
        ).todos?.apiTodoListToTodoEntityList()

        return if (pageNumber == DEFAULT_PAGE_NUM) {
            if (todos != null) {
                database.deleteAllAndInsertAll(todos)
            }
            database.getAllTodos().first()
        } else {
            todos
        }

    }
    suspend fun getTodoFromLocal() : List<TodoEntity>{
        return database.getAllTodos().first()
    }

    suspend fun getTodoById(todoId : Int) : TodoEntity? {
        return database.getTodoById(todoId) ?: network.getTodoById().apiTodoToTodoEntity()
    }
}