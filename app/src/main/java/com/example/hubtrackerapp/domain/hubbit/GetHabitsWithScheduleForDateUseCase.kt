package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetHabitsWithScheduleForDateUseCase (
    private val repository: HabitRepository
){
    suspend operator fun invoke(
        userId: String,
        date: LocalDate
    ): Flow<List<HabitWithProgressUi>>{
        return repository.getHabitsWithScheduleForDate(userId = userId, date = date)
    }
}