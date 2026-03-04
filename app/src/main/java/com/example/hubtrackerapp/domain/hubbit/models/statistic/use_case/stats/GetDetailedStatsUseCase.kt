package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.stats

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.DetailedStats
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.StatsPeriod
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetDetailedStatsUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        period: StatsPeriod
    ): DetailedStats = repository.getDetailedStats(userId, period)
}