package com.ramarajan.todomastercompose.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "todo",
)
data class TodoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,
    @ColumnInfo("todo_id")
    var todoId: Int?,
    @ColumnInfo("completed")
    val completed: Boolean?,
    @ColumnInfo("todo")
    val todo: String?,
    @ColumnInfo("userId")
    val userId: Int?
)