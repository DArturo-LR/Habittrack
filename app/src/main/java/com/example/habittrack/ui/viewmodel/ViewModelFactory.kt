package com.example.habittrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.data.repository.HabitRepositoryImpl
import com.example.habittrack.data.repository.ReminderRepositoryImpl
import com.example.habittrack.domain.usecase.habit.*
import com.example.habittrack.domain.usecase.reminder.*

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    private val habitRepository = HabitRepositoryImpl()
    private val reminderRepository = ReminderRepositoryImpl(application)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HabitViewModel::class.java) -> {
                HabitViewModel(
                    GetHabitsUseCase(habitRepository),
                    AddHabitUseCase(habitRepository),
                    DeleteHabitUseCase(habitRepository),
                    UpdateHabitUseCase(habitRepository),
                    UpdateHabitProgressUseCase(habitRepository)
                ) as T
            }
            modelClass.isAssignableFrom(ReminderViewModel::class.java) -> {
                ReminderViewModel(
                    application,
                    GetRemindersUseCase(reminderRepository),
                    SaveRemindersUseCase(reminderRepository)
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
