package com.example.hubtrackerapp.domain.hubbit.models.forUi

data class HabitWithProgressUi (
    val habitId: String,
    val emoji: String,
    val title: String,
    val isCompleted: Boolean,
    val progress: Float
)