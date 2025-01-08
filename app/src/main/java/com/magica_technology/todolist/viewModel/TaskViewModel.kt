package com.magica_technology.todolist.viewModel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magica_technology.todolist.repository.Repository
import com.magica_technology.todolist.room.Task
import com.magica_technology.todolist.alarm.AlarmReceiver
import com.magica_technology.todolist.utils.TaskGraph
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar

class TaskViewModel(
    private val repository: Repository = TaskGraph.repository
):ViewModel() {



    private var _taskTitleState by mutableStateOf("")
    val taskTitleState get() = _taskTitleState
    private var _taskDescriptionState by mutableStateOf("")
    val taskDescriptionState get() = _taskDescriptionState
    // For DropDownManu....................
    private var _selectedCategory by  mutableStateOf("")
    val selectedCategory get() = _selectedCategory

    private var _taskDateState by mutableStateOf("")
    val taskDateState get() = _taskDateState
    private var _taskTimeState by mutableStateOf("")
    val taskTimeState get() = _taskTimeState

    fun onTaskTitleChanged(newString:String){
        _taskTitleState = newString
    }

    fun onTaskDescriptionChanged(newString: String){
        _taskDescriptionState = newString
    }

    fun onTaskCategoryChanged(newString: String){
        _selectedCategory=newString
    }

    fun onTaskDateChanged(newString: String) {
        _taskDateState=newString
    }
    fun onTaskTimeChanged(newString: String) {
        _taskTimeState=newString
    }

    fun getTimestamp(): Long {
        if (taskDateState.isNotEmpty()) {
            val parts = taskDateState.split("-").map { it.toInt() }
            val timeParts = if (taskTimeState.isNotEmpty()) {
                taskTimeState.split(":").map { it.toInt() }
            } else {
                listOf(0, 0)
            }
            val calendar = Calendar.getInstance()
            calendar.set(parts[0], parts[1] - 1, parts[2], timeParts[0], timeParts[1], 0)
            return calendar.timeInMillis
        }
        return 0L
    }

    fun getDateTimeFromTimestamp(timestamp: Long): Pair<String, String> {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        val date = "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"

        val time = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

        return Pair(date, time)
    }




    //Room Database ............................

    private lateinit var _getAllPendingTask:Flow<List<Task>>
    val getAllPendingTask get() = _getAllPendingTask

    init {
        viewModelScope.launch {
            _getAllPendingTask = repository.getAllPendingTask()
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }
    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun addNewTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNewTask(task)
        }
    }

    fun getTaskById(id:Long):Flow<Task>{
        return repository.getTaskById(id)
    }


}