package com.example.hubtrackerapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "habit_progress",
    primaryKeys = ["habitId", "date"]
)
data class HabitProgressDbModel(
    val habitId: String,
    val date: LocalDate,
    val isCompleted: Boolean = false,
    val progress: Float = 0f,
    val progressWithTarget: String = "0", //выполненное количество
    val skipped: Boolean = false,
    val failed: Boolean = false
    )