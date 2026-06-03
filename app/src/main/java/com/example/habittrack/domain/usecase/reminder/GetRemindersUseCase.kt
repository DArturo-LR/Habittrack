package com.example.habittrack.domain.usecase.reminder

import com.example.habittrack.domain.model.Reminder
import com.example.habittrack.domain.repository.ReminderRepository

class GetRemindersUseCase(private val repository: ReminderRepository) {
    operator fun invoke(): List<Reminder> = repository.getReminders()
}