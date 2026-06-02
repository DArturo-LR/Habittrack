package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.domain.model.Habit
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.example.habittrack.ui.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class AddHabitActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_habit)

        val factory = ViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[HabitViewModel::class.java]

        val nameInput = findViewById<EditText>(R.id.etName)
        val goalInput = findViewById<EditText>(R.id.etGoal)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupCategory)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = nameInput.text.toString()
            val goalStr = goalInput.text.toString()
            if (name.isEmpty() || goalStr.isEmpty()) return@setOnClickListener
            
            val goal = goalStr.toInt()
            val selectedId = radioGroup.checkedRadioButtonId
            val category = when (selectedId) {
                R.id.rbSalud -> "Salud"
                R.id.rbEstudio -> "Estudio"
                R.id.rbPasatiempo -> "Pasatiempo"
                R.id.rbOtros -> "Otros"
                else -> "Otros"
            }

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            val habit = Habit(
                id = System.currentTimeMillis().toString(),
                userId = userId,
                name = name,
                frequency = "Diario",
                goal = goal,
                category = category
            )

            viewModel.addHabit(habit)
            finish()
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.selectedItemId = R.id.nav_add
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_progreso -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_add -> true
                R.id.nav_stats -> {
                    startActivity(Intent(this, StatsActivity::class.java))
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.nav_reminders -> {
                    startActivity(Intent(this, RemindersActivity::class.java))
                    true
                }
                else -> true
            }
        }
    }
}
