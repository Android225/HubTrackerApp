package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.streaks

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class HasProgressTodayUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(userId: String): Boolean = repository.hasProgressToday(userId)
}