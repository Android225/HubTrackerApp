package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import javax.inject.Inject

class SaveProgressUseCase @Inject constructor (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitProgress: HabitProgress
    ) {
        repository.saveProgress(habitProgress)
    }
}