package com.example.habittrack.domain.usecase.habit

import com.example.habittrack.domain.model.Habit
import com.example.habittrack.domain.repository.HabitRepository

class GetHabitsUseCase(private val repository: HabitRepository) {
    operator fun invoke(onSuccess: (List<Habit>) -> Unit) = repository.getHabits(onSuccess)
}