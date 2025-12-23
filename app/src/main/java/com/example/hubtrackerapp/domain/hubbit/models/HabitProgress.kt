package com.example.hubtrackerapp.domain.hubbit.models

import java.time.LocalDate

data class HabitProgress(
    val habitId: String,
    val userId: String,
    val date: LocalDate,
    val isCompleted: Boolean,
    val progress: Float
)