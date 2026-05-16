package com.example.habittrack.ui.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.data.model.Habit

class HabitAdapter(
    private var habits: List<Habit>,
    private val onProgressClick: (Habit) -> Unit
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvHabitName: TextView = view.findViewById(R.id.tvHabitName)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvProgress: TextView = view.findViewById(R.id.tvProgress)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val btnCompleteDay: Button = view.findViewById(R.id.btnCompleteDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)

        return HabitViewHolder(view)
    }

    override fun getItemCount(): Int = habits.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {

        val habit = habits[position]

        holder.tvHabitName.text = habit.name

        holder.tvCategory.text = "Categoría: ${habit.category}"

        holder.tvProgress.text =
            "${habit.progress} / ${habit.goal} días"

        val percentage =
            (habit.progress * 100) / habit.goal

        holder.progressBar.progress = percentage

        val today = java.time.LocalDate.now().toString()

        if (habit.progress >= habit.goal) {

            holder.btnCompleteDay.text = "Completado"
            holder.btnCompleteDay.isEnabled = false

        } else if (habit.lastCompletedDate == today) {

            holder.btnCompleteDay.text = "Ya completado hoy"
            holder.btnCompleteDay.isEnabled = false

        } else {

            holder.btnCompleteDay.text = "Completar día"
            holder.btnCompleteDay.isEnabled = true
        }

        holder.btnCompleteDay.setOnClickListener {

            onProgressClick(habit)
        }
    }

    fun updateData(newHabits: List<Habit>) {

        habits = newHabits
        notifyDataSetChanged()
    }
}