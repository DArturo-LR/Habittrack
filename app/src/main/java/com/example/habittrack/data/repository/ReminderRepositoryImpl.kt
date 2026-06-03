package com.example.habittrack.data.repository

import android.content.Context
import com.example.habittrack.domain.model.Reminder
import com.example.habittrack.domain.repository.ReminderRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReminderRepositoryImpl(private val context: Context) : ReminderRepository {

    private val sharedPreferences = context.getSharedPreferences("reminders", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun getReminders(): List<Reminder> {
        val json = sharedPreferences.getString("reminders_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Reminder>>() {}.type
        return gson.fromJson(json, type)
    }

    override fun saveReminders(reminders: List<Reminder>) {
        val json = gson.toJson(reminders)
        sharedPreferences.edit().putString("reminders_list", json).apply()
    }
}
