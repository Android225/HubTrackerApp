package com.example.hubtrackerapp.data.predefined

import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.PredefinedHabitRepository

object PredefinedHabitRepositoryImpl: PredefinedHabitRepository {
    override suspend fun getAll(): List<PredefinedHabit> {
        return PredefinedHabitData.habits
    }
}