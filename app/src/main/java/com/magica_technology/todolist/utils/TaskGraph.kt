package com.magica_technology.todolist.utils

import android.content.Context
import androidx.room.Room
import com.magica_technology.todolist.repository.Repository
import com.magica_technology.todolist.room.TaskDatabase

object TaskGraph {

    private lateinit var taskDatabase: TaskDatabase

    val repository by lazy {
        Repository(taskDatabase.taskDao())
    }

    fun provider(context: Context){
        taskDatabase = Room
            .databaseBuilder(context,TaskDatabase::class.java,"taskooo.db")
            .build()
    }



}