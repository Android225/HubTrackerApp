package com.example.hubtrackerapp.domain.hubbit

import java.time.LocalDate
import javax.inject.Inject

class SwitchCompleteStatusUseCase @Inject constructor (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitId: String,
        date: LocalDate
    ){
        repository.switchCompleteStatus(habitId,date)
    }
}