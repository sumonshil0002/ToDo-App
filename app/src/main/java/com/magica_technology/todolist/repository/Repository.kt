package com.magica_technology.todolist.repository

import com.magica_technology.todolist.room.Task
import com.magica_technology.todolist.room.TaskDao
import kotlinx.coroutines.flow.Flow

class Repository(private val taskDao: TaskDao) {

    fun getAllPendingTask():Flow<List<Task>>{
        return taskDao.getAllPendingTask()
    }

    fun getTaskById(id:Long):Flow<Task>{
        return taskDao.getTaskById(id)
    }
    suspend fun addNewTask(task: Task){
        taskDao.addNewTask(task)
    }
    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }
    suspend fun deleteTask(task: Task){
        taskDao.deleteTask(task)
    }

}