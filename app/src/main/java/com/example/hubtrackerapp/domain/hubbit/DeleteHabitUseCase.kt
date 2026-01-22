package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor (
    private val repository: HabitRepository
) {
    suspend operator fun invoke(
        habitId: String
    ){
        repository.deleteHabit(habitId)
    }
}