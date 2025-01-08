package com.magica_technology.todolist.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todo-list")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Long =0L,
    @ColumnInfo("task-title")
    val title:String,
    @ColumnInfo("task-desc")
    val description : String,
    @ColumnInfo("task-cat")
    val category:String="",
    @ColumnInfo("task-time")
    val dueTime:Long,
    @ColumnInfo("task-isCom")
    val isCompleted:Int = 0
)
