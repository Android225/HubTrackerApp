package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitRepository {

    fun getAllHabits(): Flow<List<HabitUi>>

    suspend fun switchCompleteStatusInThisDate(habitId: String, date: LocalDate)

    suspend fun getHabit(habitId: String): HabitUi

    suspend fun changeSchedule(habitId: String, schedule: HabitSchedule)

    suspend fun deleteHabit(habitId: String)

    suspend fun editHabit(habit: HabitUi)

    suspend fun addHabit(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule
    )

    suspend fun saveProgress(
        habitId: String,
        date: LocalDate,
        isCompleted: Boolean,
        progress: Float
    )

}