package com.example.habittrack.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.ui.viewmodel.HabitViewModel
import com.example.habittrack.ui.view.adapter.HabitAdapter

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

        findViewById<Button>(R.id.btnAddHabit).setOnClickListener {

            startActivity(Intent(this, AddHabitActivity::class.java))
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.loadHabits()
    }
}