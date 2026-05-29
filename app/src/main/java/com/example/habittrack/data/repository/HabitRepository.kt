package com.example.habittrack.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.habittrack.data.model.Habit
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class HabitRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addHabit(habit: Habit) {

        db.collection("habits")
            .document(habit.id)
            .set(habit)
    }

    fun getHabits(habitsLiveData: MutableLiveData<List<Habit>>) {

        val userId = FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid

        db.collection("habits")
            .whereEqualTo("userId", userId)
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
    fun deleteHabit(habitId: String) {

        db.collection("habits")
            .document(habitId)
            .delete()
    }

    fun updateHabit(habit: Habit) {

        db.collection("habits")
            .document(habit.id)
            .set(habit)
    }

    fun updateHabitProgress(habit: Habit) {

        db.collection("habits")
            .document(habit.id)
            .update(
                mapOf(
                    "progress" to habit.progress,
                    "lastCompletedDate" to habit.lastCompletedDate,
                    "streak" to habit.streak
                )
            )
    }
}