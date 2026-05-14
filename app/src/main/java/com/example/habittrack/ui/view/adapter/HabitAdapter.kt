package com.example.habittrack.ui.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.data.model.Habit

class HabitAdapter(
    private var habitList: List<Habit>
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvHabitName: TextView = view.findViewById(R.id.tvHabitName)
        val tvHabitGoal: TextView = view.findViewById(R.id.tvHabitGoal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)

        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {

        val habit = habitList[position]

        holder.tvHabitName.text = habit.name
        holder.tvHabitGoal.text = "Meta: ${habit.goal} días"
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    fun updateData(newList: List<Habit>) {
        habitList = newList
        notifyDataSetChanged()
    }
}