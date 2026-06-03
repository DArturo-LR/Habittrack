package com.example.habittrack.domain.model

data class Habit(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val frequency: String = "",
    val goal: Int = 0,
    val category: String = "",
    val progress: Int = 0,
    val streak: Int = 0,
    val lastCompletedDate: String = ""
)