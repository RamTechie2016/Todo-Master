package com.ramarajan.todomastercompose.data.local.service

import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    //Caching
    fun getAllTodos(): Flow<List<TodoEntity>>
    fun getTodoById(todoId : Int): TodoEntity?
    fun deleteAllAndInsertAll(articles: List<TodoEntity>)
}