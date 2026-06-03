package com.example.habittrack.domain.usecase.habit

import com.example.habittrack.domain.model.Habit
import com.example.habittrack.domain.repository.HabitRepository

class AddHabitUseCase(private val repository: HabitRepository) {
    operator fun invoke(habit: Habit) = repository.addHabit(habit)
}