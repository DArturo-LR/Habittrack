package com.example.habittrack.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.habittrack.data.model.Habit
import com.google.firebase.firestore.FirebaseFirestore

class HabitRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addHabit(habit: Habit) {

        db.collection("habits")
            .document(habit.id)
            .set(habit)
    }

    fun getHabits(habitsLiveData: MutableLiveData<List<Habit>>) {

        db.collection("habits")
            .get()
            .addOnSuccessListener { result ->

                val habitsList = mutableListOf<Habit>()

                for (document in result) {

                    val habit =
                        document.toObject(Habit::class.java)

                    habitsList.add(habit)
                }

                habitsLiveData.value = habitsList
            }
    }

    fun updateHabitProgress(habit: Habit) {

        db.collection("habits")
            .document(habit.id)
            .set(habit)
    }
}