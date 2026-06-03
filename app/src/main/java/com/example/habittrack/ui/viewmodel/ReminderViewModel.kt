package com.example.habittrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.habittrack.domain.model.Reminder
import com.example.habittrack.domain.usecase.reminder.GetRemindersUseCase
import com.example.habittrack.domain.usecase.reminder.SaveRemindersUseCase
import com.example.habittrack.ui.notification.NotificationHelper

class ReminderViewModel(
    application: Application,
    private val getRemindersUseCase: GetRemindersUseCase,
    private val saveRemindersUseCase: SaveRemindersUseCase
) : AndroidViewModel(application) {

    val reminders = MutableLiveData<List<Reminder>>()

    fun loadReminders() {
        reminders.value = getRemindersUseCase()
    }

    fun addReminder(reminder: Reminder) {
        val currentList = reminders.value?.toMutableList() ?: mutableListOf()
        currentList.add(reminder)
        saveRemindersUseCase(currentList)
        
        NotificationHelper.scheduleDailyNotification(
            getApplication(),
            reminder.hour,
            reminder.minute,
            reminder.id,
            reminder.title
        )
        loadReminders()
    }

    fun toggleReminder(reminder: Reminder, enabled: Boolean) {
        val currentList = reminders.value?.toMutableList() ?: return
        val index = currentList.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            val updatedReminder = reminder.copy(enabled = enabled)
            currentList[index] = updatedReminder
            saveRemindersUseCase(currentList)

            if (enabled) {
                NotificationHelper.scheduleDailyNotification(
                    getApplication(),
                    updatedReminder.hour,
                    updatedReminder.minute,
                    updatedReminder.id,
                    updatedReminder.title
                )
            } else {
                NotificationHelper.cancelNotification(getApplication(), updatedReminder.id)
            }
            loadReminders()
        }
    }

    fun updateReminderTime(reminder: Reminder, hour: Int, minute: Int) {
        val currentList = reminders.value?.toMutableList() ?: return
        val index = currentList.indexOfFirst { it.id == reminder.id }
        if (index != -1) {
            val updatedReminder = reminder.copy(hour = hour, minute = minute)
            currentList[index] = updatedReminder
            saveRemindersUseCase(currentList)

            NotificationHelper.cancelNotification(getApplication(), reminder.id)
            NotificationHelper.scheduleDailyNotification(
                getApplication(),
                hour,
                minute,
                reminder.id,
                reminder.title
            )
            loadReminders()
        }
    }

    fun deleteReminder(reminder: Reminder) {
        val currentList = reminders.value?.toMutableList() ?: return
        currentList.remove(reminder)
        saveRemindersUseCase(currentList)
        
        NotificationHelper.cancelNotification(getApplication(), reminder.id)
        loadReminders()
    }
}
