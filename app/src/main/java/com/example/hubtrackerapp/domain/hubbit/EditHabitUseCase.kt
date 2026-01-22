package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import javax.inject.Inject

class EditHabitUseCase @Inject constructor (
    private val repository: HabitRepository
) {
    suspend operator fun invoke(
        habit: HabitUi
    ){
        repository.editHabit(habit)
    }
}