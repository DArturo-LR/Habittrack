package com.example.habittrack.ui.view

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.domain.model.Reminder
import com.example.habittrack.ui.view.adapter.ReminderAdapter
import com.example.habittrack.ui.viewmodel.ReminderViewModel
import com.example.habittrack.ui.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class RemindersActivity : AppCompatActivity() {

    private lateinit var viewModel: ReminderViewModel
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        val factory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[ReminderViewModel::class.java]

        val recyclerReminders = findViewById<RecyclerView>(R.id.recyclerReminders)
        val btnAddReminder = findViewById<Button>(R.id.btnAddReminder)

        adapter = ReminderAdapter(
            emptyList(),
            onToggle = { reminder, enabled ->
                viewModel.toggleReminder(reminder, enabled)
            },
            onEdit = { reminder ->
                TimePickerDialog(this, { _, hour, minute ->
                    viewModel.updateReminderTime(reminder, hour, minute)
                }, reminder.hour, reminder.minute, true).show()
            },
            onDelete = { reminder ->
                viewModel.deleteReminder(reminder)
            }
        )

        recyclerReminders.layoutManager = LinearLayoutManager(this)
        recyclerReminders.adapter = adapter

        viewModel.reminders.observe(this) { reminders ->
            adapter.updateData(reminders)
        }

        viewModel.loadReminders()

        btnAddReminder.setOnClickListener {
            val editText = EditText(this)
            editText.hint = getString(R.string.reminder_name)

            AlertDialog.Builder(this)
                .setTitle(R.string.new_reminder)
                .setView(editText)
                .setPositiveButton(R.string.continue_text) { _, _ ->
                    val title = editText.text.toString()
                    TimePickerDialog(this, { _, hour, minute ->
                        val reminder = Reminder(
                            id = System.currentTimeMillis().toString(),
                            title = title,
                            hour = hour,
                            minute = minute,
                            enabled = true
                        )
                        viewModel.addReminder(reminder)
                    }, 20, 0, true).show()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_reminders
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_progreso -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_add -> {
                    startActivity(Intent(this, AddHabitActivity::class.java))
                    true
                }
                R.id.nav_stats -> {
                    startActivity(Intent(this, StatsActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.nav_reminders -> true
                else -> true
            }
        }
    }
}
