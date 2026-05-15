package com.example.habittrack.data.model

data class Habit(
    val id: String = "",
    val name: String = "",
    val frequency: String = "",
    val goal: Int = 0,
    val progress: Int = 0,
    val category: String = ""
)