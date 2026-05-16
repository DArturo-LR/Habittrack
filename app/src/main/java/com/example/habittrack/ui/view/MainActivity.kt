package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.data.model.Habit
import com.example.habittrack.ui.view.adapter.HabitAdapter
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    private lateinit var adapter: HabitAdapter

    private var allHabits = listOf<Habit>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel =
            ViewModelProvider(this)[HabitViewModel::class.java]

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerHabits)

        adapter = HabitAdapter(emptyList()) { habit ->

            val today = LocalDate.now().toString()

            if (
                habit.progress < habit.goal &&
                habit.lastCompletedDate != today
            ) {

                val updatedHabit = habit.copy(

                    progress = habit.progress + 1,

                    lastCompletedDate = today,

                    streak = habit.streak + 1
                )

                viewModel.updateProgress(updatedHabit)

                allHabits = allHabits.map {

                    if (it.id == updatedHabit.id) {
                        updatedHabit
                    } else {
                        it
                    }
                }

                adapter.updateData(allHabits)
            }
        }

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        recyclerView.adapter = adapter

        viewModel.habits.observe(this) { habits ->

            allHabits = habits

            adapter.updateData(habits)
        }

        viewModel.loadHabits()

        findViewById<Button>(R.id.btnTodos)
            .setOnClickListener {

                adapter.updateData(allHabits)
            }

        findViewById<Button>(R.id.btnSalud)
            .setOnClickListener {

                adapter.updateData(

                    allHabits.filter {

                        it.category == "Salud"
                    }
                )
            }

        findViewById<Button>(R.id.btnEstudio)
            .setOnClickListener {

                adapter.updateData(

                    allHabits.filter {

                        it.category == "Estudio"
                    }
                )
            }

        findViewById<Button>(R.id.btnPasatiempo)
            .setOnClickListener {

                adapter.updateData(

                    allHabits.filter {

                        it.category == "Pasatiempo"
                    }
                )
            }

        findViewById<Button>(R.id.btnOtros)
            .setOnClickListener {

                adapter.updateData(

                    allHabits.filter {

                        it.category == "Otros"
                    }
                )
            }

        val bottomNav =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        bottomNav.selectedItemId = R.id.nav_progreso

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_add -> {

                    startActivity(

                        Intent(
                            this,
                            AddHabitActivity::class.java
                        )
                    )

                    true
                }

                R.id.nav_progreso -> {

                    true
                }
                R.id.nav_stats -> {

                    startActivity(
                        Intent(this, StatsActivity::class.java)
                    )

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