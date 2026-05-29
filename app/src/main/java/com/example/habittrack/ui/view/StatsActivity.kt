package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.data.model.Habit
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class StatsActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stats)

        viewModel =
            ViewModelProvider(this)[HabitViewModel::class.java]

        val tvCompleted =
            findViewById<TextView>(R.id.tvCompleted)

        val tvInProgress =
            findViewById<TextView>(R.id.tvInProgress)

        val tvBestStreak =
            findViewById<TextView>(R.id.tvBestStreak)

        val tvTotalProgress =
            findViewById<TextView>(R.id.tvTotalProgress)

        viewModel.habits.observe(this) { habits ->

            updateStats(
                habits,
                tvCompleted,
                tvInProgress,
                tvBestStreak,
                tvTotalProgress
            )
        }

        viewModel.loadHabits()

        val bottomNav =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        bottomNav.selectedItemId = R.id.nav_stats

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_progreso -> {

                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )

                    finish()

                    true
                }

                R.id.nav_add -> {

                    startActivity(
                        Intent(this, AddHabitActivity::class.java)
                    )

                    finish()

                    true
                }

                R.id.nav_stats -> {
                    true
                }
                R.id.nav_profile -> {

                    startActivity(
                        Intent(this, ProfileActivity::class.java)
                    )

                    true
                }

                R.id.nav_reminders -> {

                    startActivity(
                        Intent(this, RemindersActivity::class.java)
                    )

                    true
                }

                else -> {
                    true
                }
            }
        }
    }

    private fun updateStats(
        habits: List<Habit>,
        tvCompleted: TextView,
        tvInProgress: TextView,
        tvBestStreak: TextView,
        tvTotalProgress: TextView
    ) {

        val completed =
            habits.count {
                it.progress >= it.goal
            }

        val inProgress =
            habits.count {
                it.progress < it.goal
            }

        val bestStreak =
            habits.maxOfOrNull {
                it.streak
            } ?: 0

        val totalGoal =
            habits.sumOf {
                it.goal
            }

        val totalProgress =
            habits.sumOf {
                it.progress
            }

        val percentage =
            if (totalGoal > 0) {
                (totalProgress * 100) / totalGoal
            } else {
                0
            }

        tvCompleted.text =
            "Hábitos completados: $completed"

        tvInProgress.text =
            "Hábitos en progreso: $inProgress"

        tvBestStreak.text =
            "Mejor racha: $bestStreak días"

        tvTotalProgress.text =
            "Progreso total: $percentage%"
    }
}