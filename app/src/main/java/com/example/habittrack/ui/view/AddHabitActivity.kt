package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.data.model.Habit
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

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

            val goal = goalInput.text.toString().toIntOrNull() ?: 0

            if (name.isEmpty() || goal <= 0) {

                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val habit = Habit(
                id = System.currentTimeMillis().toString(),
                name = name,
                frequency = "Diario",
                goal = goal
            )

            viewModel.addHabit(habit)

            finish()
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNav.selectedItemId = R.id.nav_add

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_progreso -> {

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }

                R.id.nav_add -> {
                    true
                }

                else -> {
                    true
                }
            }
        }
    }
}