package com.example.hubtrackerapp.domain.predefined

import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import javax.inject.Inject

class GetAllPredefinedHabitsUseCase @Inject constructor (
    private val repository: PredefinedHabitRepository
) {
    suspend operator fun invoke(): List<PredefinedHabit>{
        return repository.getAll()
    }
}