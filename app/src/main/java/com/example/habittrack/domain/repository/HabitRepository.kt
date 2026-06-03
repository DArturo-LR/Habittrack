package com.example.habittrack.domain.repository

import com.example.habittrack.domain.model.Habit

interface HabitRepository {
    fun addHabit(habit: Habit)
    fun getHabits(onSuccess: (List<Habit>) -> Unit)
    fun deleteHabit(habitId: String)
    fun updateHabit(habit: Habit)
    fun updateHabitProgress(habit: Habit)
}
