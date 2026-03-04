package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.points

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetPointsEarnedInRangeUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Int = repository.getPointsEarnedInRange(userId, fromTimestamp, toTimestamp)
}