package com.example.habittrack.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittrack.domain.model.Habit
import com.example.habittrack.domain.usecase.habit.*

class HabitViewModel(
    private val getHabitsUseCase: GetHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val updateHabitProgressUseCase: UpdateHabitProgressUseCase
) : ViewModel() {

    val habits = MutableLiveData<List<Habit>>()

    fun addHabit(habit: Habit) {
        addHabitUseCase(habit)
    }

    fun loadHabits() {
        getHabitsUseCase { 
            habits.value = it
        }
    }

    fun updateProgress(habit: Habit) {
        updateHabitProgressUseCase(habit)
    }

    fun deleteHabit(habitId: String) {
        deleteHabitUseCase(habitId)
    }

    fun updateHabit(habit: Habit) {
        updateHabitUseCase(habit)
    }
}
