package com.example.hubtrackerapp.domain.hubbit

import java.time.LocalDate

class SwitchCompleteStatusUseCase (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitId: String,
        date: LocalDate
    ){
        repository.switchCompleteStatus(habitId,date)
    }
}