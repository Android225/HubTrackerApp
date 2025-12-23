package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import java.time.LocalDate

class GetProgressUseCase (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitId: String,
        date: LocalDate
    ) : HabitProgress?{
        return repository.getProgress(habitId,date)
    }
}