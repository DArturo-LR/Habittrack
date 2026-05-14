package com.example.habittrack.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.data.model.Habit
import com.example.habittrack.ui.viewmodel.HabitViewModel

class AddHabitActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        val nameInput = findViewById<EditText>(R.id.etName)
        val goalInput = findViewById<EditText>(R.id.etGoal)

        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val name = nameInput.text.toString()

            val goal = goalInput.text.toString().toInt()

            val habit = Habit(
                id = System.currentTimeMillis().toString(),
                name = name,
                frequency = "Diario",
                goal = goal
            )

            viewModel.addHabit(habit)

            finish()
        }
    }
}