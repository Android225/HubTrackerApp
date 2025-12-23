package com.example.hubtrackerapp.domain.hubbit.models

import java.time.LocalDate

data class HabitProgress(
    val habitId: String,
    val date: LocalDate,
    val isCompleted: Boolean = false,
    val progress: Float = 0f
)