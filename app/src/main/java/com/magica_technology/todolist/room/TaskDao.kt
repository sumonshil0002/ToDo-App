package com.magica_technology.todolist.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
abstract class TaskDao {

    @Query("SELECT * FROM `todo-list` WHERE `task-isCom`== 0 ORDER BY `task-time` ASC")
    abstract fun getAllPendingTask():Flow<List<Task>>

    @Query("SELECT * FROM `todo-list` WHERE id=:id")
    abstract fun getTaskById(id:Long):Flow<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun addNewTask(task:Task)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    abstract fun updateTask(task:Task)

    @Delete
    abstract fun deleteTask(task:Task)


}