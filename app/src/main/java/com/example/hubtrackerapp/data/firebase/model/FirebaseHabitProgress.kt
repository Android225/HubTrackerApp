package com.example.hubtrackerapp.data.firebase.model

data class FirebaseHabitProgress(
    val habitId: String = "",
    val date: String = "", // LocalDate -> String "2000-01-01"
    val isCompleted: Boolean = false,
    val progress: Float = 0f,
    val progressWithTarget: String = "0",
    val skipped: Boolean = false,
    val failed: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)