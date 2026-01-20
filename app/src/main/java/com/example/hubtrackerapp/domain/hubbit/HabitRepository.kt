package com.example.hubtrackerapp.domain.hubbit

import HabitMetric
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.user.User
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface HabitRepository {

    fun getHabitsWithScheduleForDate(userId: String, date: LocalDate): Flow<List<HabitWithProgressUi>>


    suspend fun getHabit(userId: String,habitId: String): HabitUi

    suspend fun changeSchedule(habitId: String, schedule: HabitSchedule)

    suspend fun deleteHabit(habitId: String)

    suspend fun editHabit(habit: HabitUi)

    suspend fun addHabit(
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
    )
    suspend fun addProgressForHabit(
        habitId: String,
        date: LocalDate,

    ): HabitProgress
    suspend fun saveProgress(
        habitProgress: HabitProgress
    )

    suspend fun  getProgressForHabitInDate(
        habitId: String,
        date: LocalDate
    ): HabitProgress

    suspend fun getProgress(
        habitId: String,
        date: LocalDate
    ): HabitProgress?

    suspend fun switchCompleteStatus(
        habitId: String,
        date: LocalDate
    )
    suspend fun getUserId(): String

    suspend fun getUserCard(userId: String): User
}