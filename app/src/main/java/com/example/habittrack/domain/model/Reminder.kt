package com.example.habittrack.domain.model

import java.io.Serializable

data class Reminder(
    val id: String = "",
    val title: String = "",
    val hour: Int = 20,
    val minute: Int = 0,
    val enabled: Boolean = true
) : Serializable