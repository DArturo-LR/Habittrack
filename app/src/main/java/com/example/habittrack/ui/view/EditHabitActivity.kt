package com.example.habittrack.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.example.habittrack.ui.viewmodel.ViewModelFactory

class EditHabitActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_habit)

        val factory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[HabitViewModel::class.java]

        val etName = findViewById<EditText>(R.id.etEditName)
        val etGoal = findViewById<EditText>(R.id.etEditGoal)
        val btnSave = findViewById<Button>(R.id.btnUpdateHabit)

        val habitId = intent.getStringExtra("habitId") ?: ""

        viewModel.habits.observe(this) { habits ->
            val habit = habits.find { it.id == habitId }
            if (habit != null) {
                etName.setText(habit.name)
                etGoal.setText(habit.goal.toString())

                btnSave.setOnClickListener {
                    val updatedHabit = habit.copy(
                        name = etName.text.toString(),
                        goal = etGoal.text.toString().toIntOrNull() ?: habit.goal
                    )
                    viewModel.updateHabit(updatedHabit)
                    finish()
                }
            }
        }

        viewModel.loadHabits()
    }
}
