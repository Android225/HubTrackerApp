package com.example.hubtrackerapp.data.db.model

import HabitMetric
import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "habits")
data class HabitDbModel(
    @PrimaryKey
    val habitId: String,
    val userId: String,
    val emoji: String,
    val title: String,
    val createdAt: LocalDate,
    val schedule: HabitScheduleDbModel,
    val isArchived: Boolean = false,
    val color: Color,
    val target: String,
    val metric: HabitMetric,
    val reminderTime: LocalTime,
    val reminderDate: HabitScheduleDbModel,
    val reminderIsActive: Boolean = false,
    val habitType: ModeForSwitchInHabit,
    val habitCustom: Boolean = false
)