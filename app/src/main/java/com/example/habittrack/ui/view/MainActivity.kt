package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.ui.view.adapter.HabitAdapter
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HabitViewModel
    private lateinit var adapter: HabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[HabitViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerHabits)

        adapter = HabitAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.habits.observe(this) { habits ->
            adapter.updateData(habits)
        }

        viewModel.loadHabits()

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNav.selectedItemId = R.id.nav_progreso

        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                // IR A AÑADIR HÁBITO
                R.id.nav_add -> {

                    startActivity(Intent(this, AddHabitActivity::class.java))
                    true
                }

                // PANTALLA PRINCIPAL
                R.id.nav_progreso -> {
                    true
                }

                // LOGOUT TEMPORAL EN PERFIL
                R.id.nav_profile -> {

                    FirebaseAuth.getInstance().signOut()

                    startActivity(Intent(this, LoginActivity::class.java))

                    finish()

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