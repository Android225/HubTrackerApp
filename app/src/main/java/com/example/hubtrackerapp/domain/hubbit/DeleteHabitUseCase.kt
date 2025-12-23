package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitUi

class DeleteHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(
        habitId: String
    ){
        repository.deleteHabit(habitId)
    }
}