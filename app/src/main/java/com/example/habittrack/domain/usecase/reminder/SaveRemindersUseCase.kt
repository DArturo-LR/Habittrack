package com.example.habittrack.domain.usecase.reminder

import com.example.habittrack.domain.model.Reminder
import com.example.habittrack.domain.repository.ReminderRepository

class SaveRemindersUseCase(private val repository: ReminderRepository) {
    operator fun invoke(reminders: List<Reminder>) = repository.saveReminders(reminders)
}