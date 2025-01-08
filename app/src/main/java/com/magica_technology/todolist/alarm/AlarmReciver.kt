package com.magica_technology.todolist.alarm

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.magica_technology.todolist.R
import com.magica_technology.todolist.screens.MainActivity

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            Log.d("MAGIC","Start in Alarm Receiver")

            showAlarmNotification(context)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showAlarmNotification(context: Context) {
        val stopIntent = Intent(context, StopAlarmReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            context, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent to open MainActivity when the notification is clicked
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "alarm_channel")
            .setSmallIcon(R.drawable.ic_access) // Replace with your app icon
            .setContentTitle("Alarm Triggered")
            .setContentText("You have a pending scheduled!")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
            .addAction(R.drawable.baseline_stop_circle_24, "Stop Alarm", stopPendingIntent)
            .build()


        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notification)
        Log.d("MAGIC","Show Notification Done")

    }

}