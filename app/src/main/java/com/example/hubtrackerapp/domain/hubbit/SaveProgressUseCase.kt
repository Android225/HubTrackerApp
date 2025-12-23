package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress

class SaveProgressUseCase (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitProgress: HabitProgress
    ) {
        repository.saveProgress(habitProgress)
    }
}