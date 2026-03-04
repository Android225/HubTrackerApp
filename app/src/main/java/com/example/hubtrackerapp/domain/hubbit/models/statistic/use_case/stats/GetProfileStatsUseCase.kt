package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.stats

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ProfileStats
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetProfileStatsUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(userId: String): ProfileStats =
        repository.getProfileStats(userId)
}