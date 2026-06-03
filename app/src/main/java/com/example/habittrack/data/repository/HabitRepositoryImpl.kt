package com.example.habittrack.data.repository

import com.example.habittrack.domain.model.Habit
import com.example.habittrack.domain.repository.HabitRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class HabitRepositoryImpl : HabitRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun addHabit(habit: Habit) {
        db.collection("habits")
            .document(habit.id)
            .set(habit)
    }

    override fun getHabits(onSuccess: (List<Habit>) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("habits")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { result ->
                val habitsList = result.map { it.toObject(Habit::class.java) }
                onSuccess(habitsList)
            }
    }

    override fun deleteHabit(habitId: String) {
        db.collection("habits")
            .document(habitId)
            .delete()
    }

    override fun updateHabit(habit: Habit) {
        db.collection("habits")
            .document(habit.id)
            .set(habit)
    }

    override fun updateHabitProgress(habit: Habit) {
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
