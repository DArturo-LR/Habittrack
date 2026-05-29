package com.example.habittrack.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittrack.R
import com.example.habittrack.data.model.Reminder

class ReminderAdapter(

    private var reminders: List<Reminder>,

    private val onToggle: (Reminder, Boolean) -> Unit,

    private val onEdit: (Reminder) -> Unit,

    private val onDelete: (Reminder) -> Unit

) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val tvTitle: TextView =
            view.findViewById(R.id.tvReminderTitle)

        val tvTime: TextView =
            view.findViewById(R.id.tvReminderTime)

        val switchReminder: Switch =
            view.findViewById(R.id.switchReminder)

        val btnEdit: Button =
            view.findViewById(R.id.btnEditReminder)

        val btnDelete: Button =
            view.findViewById(R.id.btnDeleteReminder)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReminderViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)

        return ReminderViewHolder(view)
    }

    override fun getItemCount(): Int = reminders.size

    override fun onBindViewHolder(
        holder: ReminderViewHolder,
        position: Int
    ) {

        val reminder = reminders[position]

        holder.tvTitle.text = reminder.title

        holder.tvTime.text =
            "${reminder.hour}:${reminder.minute}"

        holder.switchReminder.isChecked =
            reminder.enabled

        holder.switchReminder.setOnCheckedChangeListener {
                _, isChecked ->

            onToggle(reminder, isChecked)
        }

        holder.btnEdit.setOnClickListener {

            onEdit(reminder)
        }

        holder.btnDelete.setOnClickListener {

            onDelete(reminder)
        }
    }

    fun updateData(newReminders: List<Reminder>) {

        reminders = newReminders

        notifyDataSetChanged()
    }
}