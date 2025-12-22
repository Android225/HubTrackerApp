package com.example.hubtrackerapp.domain.user

import java.time.LocalDate

data class HabitUi(
    val habitId: String,
    val userId: String,
    val emoji: String,
    val title: String,
    val createdAt: LocalDate,
    val schedule: HabitSchedule,
    val isArchived: Boolean = false
)