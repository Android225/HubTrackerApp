package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.points

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetTotalPointsUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(userId: String): Int = repository.getTotalPoints(userId)
}