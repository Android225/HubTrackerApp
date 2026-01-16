package com.example.hubtrackerapp.domain.predefined

import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit

interface PredefinedHabitRepository {
    suspend fun getAll(): List<PredefinedHabit>
}