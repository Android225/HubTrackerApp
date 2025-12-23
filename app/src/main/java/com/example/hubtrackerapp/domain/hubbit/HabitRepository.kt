package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitRepository {

    suspend fun getHabitsWithScheduleForDate(userId: String, date: LocalDate): Flow<List<HabitWithProgressUi>>


    suspend fun getHabit(userId: String,habitId: String): HabitUi

    suspend fun changeSchedule(habitId: String, schedule: HabitSchedule)

    suspend fun deleteHabit(habitId: String)

    suspend fun editHabit(habit: HabitUi)

    suspend fun addHabit(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule
    )
    suspend fun addProgressForHabit(
        habitId: String,
        date: LocalDate
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
}