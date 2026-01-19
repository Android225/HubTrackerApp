package com.example.hubtrackerapp.domain.hubbit

import HabitMetric
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import java.time.LocalDate
import java.time.LocalTime

class AddHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule,
        color: Color,
        target: String,
        metric: HabitMetric,
        reminderTime: LocalTime,
        reminderDate: HabitSchedule,
        reminderIsActive: Boolean,
        habitType: ModeForSwitchInHabit,
        habitCustom: Boolean
    ){
        repository.addHabit(
            emoji,
            title,
            createdAt,
            schedule,
            color,
            target,
            metric,
            reminderTime,
            reminderDate,
            reminderIsActive,
            habitType,
            habitCustom
        )
    }
}