package com.example.habittrack

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class HabitTrackApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "habit_channel",
            "Habit Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
