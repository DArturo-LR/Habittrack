package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habittrack.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class RemindersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reminders)

        val bottomNav =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        bottomNav.selectedItemId = R.id.nav_reminders

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_progreso -> {

                    startActivity(
                        Intent(this, MainActivity::class.java)
                    )

                    true
                }

                R.id.nav_add -> {

                    startActivity(
                        Intent(this, AddHabitActivity::class.java)
                    )

                    true
                }

                R.id.nav_stats -> {

                    startActivity(
                        Intent(this, StatsActivity::class.java)
                    )

                    true
                }

                R.id.nav_profile -> {

                    startActivity(
                        Intent(this, ProfileActivity::class.java)
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
}