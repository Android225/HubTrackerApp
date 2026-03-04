package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.habits

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetHabitsCompletedCountInRangeUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Int = repository.getHabitsCompletedCountInRange(userId, fromTimestamp, toTimestamp)
}