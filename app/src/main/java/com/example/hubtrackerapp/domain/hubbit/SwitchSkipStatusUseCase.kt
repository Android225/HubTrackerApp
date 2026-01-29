package com.example.hubtrackerapp.domain.hubbit

import java.time.LocalDate
import javax.inject.Inject

class SwitchSkipStatusUseCase @Inject constructor (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitId: String,
        date: LocalDate
    ){
        repository.switchSkipStatus(habitId,date)
    }
}