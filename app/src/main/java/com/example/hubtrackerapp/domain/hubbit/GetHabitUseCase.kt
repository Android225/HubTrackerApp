package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import javax.inject.Inject

class GetHabitUseCase @Inject constructor  (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        userId: String,
        habitId: String
    ) : HabitUi {
        return repository.getHabit(userId,habitId)
    }
}