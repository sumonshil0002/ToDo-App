package com.magica_technology.todolist.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat

class StopAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Handle stopping the alarm here
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(1001)
        Log.d("MAGIC","Stop Notification Done")
    }
}

