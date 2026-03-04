package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogHabitSkippedUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        habitId: String,
        habitName: String
    ): Result<UserAction> = repository.logHabitSkipped(habitId, habitName)
}