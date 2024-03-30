package com.ramarajan.todomastercompose.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ramarajan.todomastercompose.data.local.dao.TodoDao
import com.ramarajan.todomastercompose.data.local.entity.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract  class TodoDatabase : RoomDatabase() {

    abstract fun getTodoDao(): TodoDao
}