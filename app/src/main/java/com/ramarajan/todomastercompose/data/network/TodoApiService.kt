package com.ramarajan.todomastercompose.data.network

import com.ramarajan.todomastercompose.data.model.Todo
import com.ramarajan.todomastercompose.data.model.TodoResponse
import retrofit2.http.GET

interface TodoApiService {

    @GET("todos")
    suspend fun getAllTodo(
    ): TodoResponse

    @GET("todos/1")
    suspend fun getTodoById(
    ): Todo
}