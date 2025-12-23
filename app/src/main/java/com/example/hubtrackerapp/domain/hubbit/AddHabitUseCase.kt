package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import java.time.LocalDate

class AddHabitUseCase(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule
    ){
        repository.addHabit(emoji,title,createdAt,schedule)
    }
}