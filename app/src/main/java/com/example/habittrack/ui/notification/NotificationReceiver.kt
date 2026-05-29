package com.example.habittrack.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.habittrack.R
import com.example.habittrack.ui.view.MainActivity
import java.util.Calendar

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val reminderTitle = intent?.getStringExtra("REMINDER_TITLE") ?: "HabitTrack"
        val reminderId = intent?.getStringExtra("REMINDER_ID") ?: return

        val openIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, openIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val hora = String.format(
            "%02d:%02d",
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE)
        )

        val notification = NotificationCompat.Builder(context, "habit_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("⏰ Recordatorio: $reminderTitle")
            .setContentText("Es hora de cumplir tu hábito. ¡Tú puedes!")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Es hora de cumplir tu hábito.\n🕐 $hora — ¡No lo dejes para después!")
                    .setSummaryText("HabitTrack")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()

        if (
            androidx.core.app.ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(context).notify(
                reminderTitle.hashCode(),
                notification
            )
        }

        reprogramForTomorrow(context, reminderId, reminderTitle)
    }

    private fun reprogramForTomorrow(
        context: Context,
        reminderId: String,
        reminderTitle: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!alarmManager.canScheduleExactAlarms()) return

        val now = Calendar.getInstance()
        val tomorrow = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, now.get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val newIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("REMINDER_TITLE", reminderTitle)
            putExtra("REMINDER_ID", reminderId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId.hashCode(),
            newIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            tomorrow.timeInMillis,
            pendingIntent
        )
    }
}
