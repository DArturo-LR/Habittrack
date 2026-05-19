package com.example.habittrack.ui.view

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.data.model.Reminder
import com.example.habittrack.ui.notification.NotificationHelper
import com.example.habittrack.ui.view.adapter.ReminderAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RemindersActivity : AppCompatActivity() {

    private val reminders =
        mutableListOf<Reminder>()

    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reminders)

        loadReminders()

        val recyclerReminders =
            findViewById<RecyclerView>(R.id.recyclerReminders)

        val btnAddReminder =
            findViewById<Button>(R.id.btnAddReminder)

        recyclerReminders.layoutManager =
            LinearLayoutManager(this)

        adapter = ReminderAdapter(

            reminders,

            onToggle = { reminder, enabled ->

                val index =
                    reminders.indexOfFirst {
                        it.id == reminder.id
                    }

                if (index != -1) {

                    val updatedReminder =
                        reminder.copy(enabled = enabled)

                    reminders[index] =
                        updatedReminder

                    if (enabled) {

                        NotificationHelper
                            .scheduleDailyNotification(

                                this,

                                updatedReminder.hour,

                                updatedReminder.minute,

                                updatedReminder.id,

                                updatedReminder.title
                            )

                    } else {

                        NotificationHelper
                            .cancelNotification(

                                this,

                                updatedReminder.id
                            )
                    }

                    adapter.updateData(reminders)

                    saveReminders()
                }
            },

            onEdit = { reminder ->

                TimePickerDialog(
                    this,

                    { _, hour, minute ->

                        val index =
                            reminders.indexOfFirst {
                                it.id == reminder.id
                            }

                        if (index != -1) {

                            val updatedReminder =
                                reminder.copy(

                                    hour = hour,

                                    minute = minute
                                )

                            reminders[index] =
                                updatedReminder

                            NotificationHelper
                                .cancelNotification(
                                    this,
                                    reminder.id
                                )

                            NotificationHelper
                                .scheduleDailyNotification(

                                    this,

                                    hour,

                                    minute,

                                    reminder.id,

                                    reminder.title
                                )

                            adapter.updateData(reminders)

                            saveReminders()
                        }
                    },

                    reminder.hour,

                    reminder.minute,

                    true

                ).show()
            },

            onDelete = { reminder ->

                NotificationHelper
                    .cancelNotification(
                        this,
                        reminder.id
                    )

                reminders.remove(reminder)

                adapter.updateData(reminders)

                saveReminders()
            }
        )

        recyclerReminders.adapter = adapter

        btnAddReminder.setOnClickListener {

            val editText = EditText(this)

            editText.hint =
                getString(R.string.reminder_name)

            AlertDialog.Builder(this)

                .setTitle(R.string.new_reminder)

                .setView(editText)

                .setPositiveButton(
                    R.string.continue_text
                ) { _, _ ->

                    val title =
                        editText.text.toString()

                    TimePickerDialog(

                        this,

                        { _, hour, minute ->

                            val reminder =
                                Reminder(

                                    id = System.currentTimeMillis()
                                        .toString(),

                                    title = title,

                                    hour = hour,

                                    minute = minute,

                                    enabled = true
                                )

                            reminders.add(reminder)

                            NotificationHelper
                                .scheduleDailyNotification(

                                    this,

                                    hour,

                                    minute,

                                    reminder.id,

                                    reminder.title
                                )

                            adapter.updateData(reminders)

                            saveReminders()
                        },

                        20,

                        0,

                        true

                    ).show()
                }

                .setNegativeButton(
                    R.string.cancel,
                    null
                )

                .show()
        }

        val bottomNav =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        bottomNav.selectedItemId =
            R.id.nav_reminders

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_progreso -> {

                    startActivity(
                        Intent(
                            this,
                            MainActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_add -> {

                    startActivity(
                        Intent(
                            this,
                            AddHabitActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_stats -> {

                    startActivity(
                        Intent(
                            this,
                            StatsActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_profile -> {

                    startActivity(
                        Intent(
                            this,
                            ProfileActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_reminders -> {

                    true
                }

                else -> true
            }
        }
    }

    private fun saveReminders() {

        val sharedPreferences =
            getSharedPreferences(
                "reminders",
                MODE_PRIVATE
            )

        val json =
            Gson().toJson(reminders)

        sharedPreferences.edit()
            .putString(
                "reminders_list",
                json
            )
            .apply()
    }

    private fun loadReminders() {

        val sharedPreferences =
            getSharedPreferences(
                "reminders",
                MODE_PRIVATE
            )

        val json =
            sharedPreferences.getString(
                "reminders_list",
                null
            )

        if (json != null) {

            val type =
                object : TypeToken<
                        MutableList<Reminder>
                        >() {}.type

            val savedReminders:
                    MutableList<Reminder> =
                Gson().fromJson(json, type)

            reminders.clear()

            reminders.addAll(savedReminders)
        }
    }
}