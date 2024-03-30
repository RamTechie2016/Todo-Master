package com.ramarajan.todomastercompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todo: List<TodoEntity>)

    @Query("SELECT * FROM todo")
    fun getAllTodo(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo WHERE todo_id = :todoId LIMIT 1")
    fun getTodoById(todoId : Int): TodoEntity


    @Query("DELETE FROM todo")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(todo: List<TodoEntity>) {
        deleteAll()
        return insertAll(todo)
    }

}
