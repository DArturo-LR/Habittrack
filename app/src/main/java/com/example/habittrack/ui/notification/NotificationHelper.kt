package com.example.habittrack.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

object NotificationHelper {

    fun scheduleDailyNotification(

        context: Context,

        savedHour: Int,

        savedMinute: Int,

        reminderId: String,

        reminderTitle: String
    ) {

        val intent = Intent(

            context,

            NotificationReceiver::class.java

        )

        intent.putExtra(

            "REMINDER_TITLE",

            reminderTitle
        )

        val pendingIntent = PendingIntent.getBroadcast(

            context,

            reminderId.hashCode(),

            intent,

            PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        val calendar = Calendar.getInstance().apply {

            set(Calendar.HOUR_OF_DAY, savedHour)

            set(Calendar.MINUTE, savedMinute)

            set(Calendar.SECOND, 0)

            if (before(Calendar.getInstance())) {

                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP,

            calendar.timeInMillis,

            AlarmManager.INTERVAL_DAY,

            pendingIntent
        )
    }

    fun cancelNotification(

        context: Context,

        reminderId: String
    ) {

        val intent = Intent(

            context,

            NotificationReceiver::class.java
        )

        val pendingIntent =
            PendingIntent.getBroadcast(

                context,

                reminderId.hashCode(),

                intent,

                PendingIntent.FLAG_IMMUTABLE
            )

        val alarmManager =
            context.getSystemService(
                Context.ALARM_SERVICE
            ) as AlarmManager

        alarmManager.cancel(pendingIntent)
    }
}