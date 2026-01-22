package com.example.hubtrackerapp.data.predefined

import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.PredefinedHabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PredefinedHabitRepositoryImpl @Inject constructor(): PredefinedHabitRepository {
    override suspend fun getAll(): List<PredefinedHabit> {
        return PredefinedHabitData.habits
    }
}