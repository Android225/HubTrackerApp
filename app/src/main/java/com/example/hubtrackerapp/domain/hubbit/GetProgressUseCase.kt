package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import java.time.LocalDate
import javax.inject.Inject

class GetProgressUseCase @Inject constructor  (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        habitId: String,
        date: LocalDate
    ) : HabitProgress?{
        return repository.getProgress(habitId,date)
    }
}