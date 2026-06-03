package com.example.habittrack.domain.repository

import com.example.habittrack.domain.model.Reminder

interface ReminderRepository {
    fun getReminders(): List<Reminder>
    fun saveReminders(reminders: List<Reminder>)
}
