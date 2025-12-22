package com.example.hubtrackerapp.domain.user

import java.time.LocalDate

data class HabitProgress(
    val habitId: String,
    val userId: String,
    val date: LocalDate,
    val isCompleted: Boolean,
    val progress: Float
)