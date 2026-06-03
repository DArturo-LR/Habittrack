package com.example.habittrack.domain.usecase.habit

import com.example.habittrack.domain.repository.HabitRepository

class DeleteHabitUseCase(private val repository: HabitRepository) {
    operator fun invoke(habitId: String) = repository.deleteHabit(habitId)
}