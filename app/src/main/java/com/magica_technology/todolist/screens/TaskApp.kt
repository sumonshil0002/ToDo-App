package com.magica_technology.todolist.screens

import Helper
import android.app.Application
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.magica_technology.todolist.utils.TaskGraph


class TaskApp:Application() {


    override fun onCreate() {
        super.onCreate()
        TaskGraph.provider(context = this)
        Helper.createNotificationChannel(this)


    }

}