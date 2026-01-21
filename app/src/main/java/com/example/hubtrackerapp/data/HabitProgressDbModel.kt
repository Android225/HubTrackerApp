package com.example.hubtrackerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class HabitProgressDbModel(
    @PrimaryKey
    val habitId: String,
    val date: LocalDate,
    val isCompleted: Boolean = false,
    val progress: Float = 0f,
    val progressWithTarget: String = "0", //выполненное количество
    val skipped: Boolean = false,
    val failed: Boolean = false
    )