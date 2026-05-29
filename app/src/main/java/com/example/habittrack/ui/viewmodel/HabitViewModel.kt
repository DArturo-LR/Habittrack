package com.example.habittrack.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittrack.data.model.Habit
import com.example.habittrack.data.repository.HabitRepository

class HabitViewModel : ViewModel() {

    private val repository = HabitRepository()

    val habits = MutableLiveData<List<Habit>>()

    fun addHabit(habit: Habit) {
        repository.addHabit(habit)
    }

    fun loadHabits() {
        repository.getHabits(habits)
    }
    fun updateProgress(habit: Habit) {

        repository.updateHabitProgress(habit)
    }
    fun deleteHabit(habitId: String) {

        repository.deleteHabit(habitId)
    }

    fun updateHabit(habit: Habit) {

        repository.updateHabit(habit)
    }
}