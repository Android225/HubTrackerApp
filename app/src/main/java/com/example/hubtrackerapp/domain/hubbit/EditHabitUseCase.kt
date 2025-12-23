package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitUi

class EditHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(
        habit: HabitUi
    ){
        repository.editHabit(habit)
    }
}