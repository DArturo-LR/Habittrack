package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.ui.view.adapter.HabitAdapter
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var adapter: HabitAdapter

    private var allHabits = listOf<com.example.habittrack.data.model.Habit>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerHabits)

        adapter = HabitAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.habits.observe(this) { habits ->

            allHabits = habits
            adapter.updateData(habits)
        }

        viewModel.loadHabits()

        findViewById<Button>(R.id.btnTodos).setOnClickListener {
            adapter.updateData(allHabits)
        }

        findViewById<Button>(R.id.btnSalud).setOnClickListener {
            adapter.updateData(allHabits.filter { it.category == "Salud" })
        }

        findViewById<Button>(R.id.btnEstudio).setOnClickListener {
            adapter.updateData(allHabits.filter { it.category == "Estudio" })
        }

        findViewById<Button>(R.id.btnPasatiempo).setOnClickListener {
            adapter.updateData(allHabits.filter { it.category == "Pasatiempo" })
        }

        findViewById<Button>(R.id.btnOtros).setOnClickListener {
            adapter.updateData(allHabits.filter { it.category == "Otros" })
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNav.selectedItemId = R.id.nav_progreso

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_add -> {

                    startActivity(Intent(this, AddHabitActivity::class.java))
                    true
                }

                R.id.nav_progreso -> {
                    true
                }

                else -> {
                    true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHabits()
    }
}