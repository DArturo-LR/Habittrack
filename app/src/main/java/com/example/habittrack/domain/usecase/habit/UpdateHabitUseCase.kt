package com.example.habittrack.domain.usecase.habit

import com.example.habittrack.domain.model.Habit
import com.example.habittrack.domain.repository.HabitRepository

class UpdateHabitUseCase(private val repository: HabitRepository) {
    operator fun invoke(habit: Habit) = repository.updateHabit(habit)
}