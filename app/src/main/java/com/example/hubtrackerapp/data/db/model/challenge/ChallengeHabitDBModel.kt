package com.example.hubtrackerapp.data.db.model.challenge

import HabitMetric
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "challenge_habits",
    primaryKeys = ["challengeId", "habitId"]
)
data class ChallengeHabitDBModel(
    val challengeId: String,
    val habitId: String,
    val habitName: String,
    val habitIcon: String,
    val habitColor: Int,
    val metric: HabitMetric,
    val target: String,
    val scheduleType: String,
    val scheduleValue: String?,
    val activeFromDate: Long,
    val activeToDate: Long?,
    val createdAt: Long
)
