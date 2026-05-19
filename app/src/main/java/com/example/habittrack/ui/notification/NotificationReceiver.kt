package com.example.habittrack.ui.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.habittrack.R
import com.example.habittrack.ui.view.MainActivity

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent?
    ) {

        val channelId = "habit_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                channelId,
                "Habit Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )

            val manager =
                context.getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(channel)
        }

        val reminderTitle =
            intent?.getStringExtra(
                "REMINDER_TITLE"
            ) ?: "HabitTrack"

        val openIntent = Intent(
            context,
            MainActivity::class.java
        )

        val pendingIntent =
            PendingIntent.getActivity(

                context,

                0,

                openIntent,

                PendingIntent.FLAG_IMMUTABLE
            )

        val notification =
            NotificationCompat.Builder(
                context,
                channelId
            )

                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle("HabitTrack")

                .setContentText(reminderTitle)

                .setContentIntent(pendingIntent)

                .setAutoCancel(true)

                .build()

        if (

            androidx.core.app.ActivityCompat
                .checkSelfPermission(

                    context,

                    android.Manifest.permission
                        .POST_NOTIFICATIONS

                ) == android.content.pm
                .PackageManager.PERMISSION_GRANTED
        ) {

            NotificationManagerCompat
                .from(context)
                .notify(

                    reminderTitle.hashCode(),

                    notification
                )
        }
    }
}