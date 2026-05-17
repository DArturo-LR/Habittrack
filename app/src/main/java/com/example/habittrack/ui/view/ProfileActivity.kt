package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrack.R
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import android.app.AlertDialog
import android.content.res.Configuration
import android.widget.LinearLayout
import java.util.Locale

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        viewModel =
            ViewModelProvider(this)[HabitViewModel::class.java]

        val tvEmail =
            findViewById<TextView>(R.id.tvEmail)

        val tvTotalHabits =
            findViewById<TextView>(R.id.tvTotalHabits)

        val tvBestStreak =
            findViewById<TextView>(R.id.tvBestStreak)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        val user =
            FirebaseAuth.getInstance().currentUser
        val layoutLanguage =
            findViewById<LinearLayout>(R.id.layoutLanguage)

        val tvCurrentLanguage =
            findViewById<TextView>(R.id.tvCurrentLanguage)
        val sharedPreferences =
            getSharedPreferences("settings", MODE_PRIVATE)

        val currentLanguage =
            sharedPreferences.getString("language", "es")

        tvCurrentLanguage.text =
            if (currentLanguage == "es") {
                "Español"
            } else {
                "English"
            }
        layoutLanguage.setOnClickListener {

            val languages =
                arrayOf("Español", "English")

            AlertDialog.Builder(this)
                .setTitle("Seleccionar idioma")
                .setItems(languages) { _, which ->

                    val languageCode =
                        if (which == 0) "es" else "en"

                    sharedPreferences
                        .edit()
                        .putString("language", languageCode)
                        .apply()

                    setLocale(languageCode)
                }
                .show()
        }

        tvEmail.text =
            user?.email ?: "Sin correo"

        viewModel.habits.observe(this) { habits ->

            tvTotalHabits.text =
                habits.size.toString()

            val bestStreak =
                habits.maxOfOrNull { it.streak } ?: 0

            tvBestStreak.text =
                bestStreak.toString()
        }

        viewModel.loadHabits()

        btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            val intent = Intent(
                this,
                LoginActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }

        val bottomNav =
            findViewById<BottomNavigationView>(
                R.id.bottomNavigation
            )

        bottomNav.selectedItemId = R.id.nav_profile

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

                    true
                }

                else -> true
            }
        }
    }
    private fun setLocale(languageCode: String) {

        val locale = Locale(languageCode)

        Locale.setDefault(locale)

        val config = Configuration()

        config.setLocale(locale)

        resources.updateConfiguration(
            config,
            resources.displayMetrics
        )

        recreate()
    }
}