package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.get_actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetUserActionsInRangeUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserAction> = repository.getUserActionsInRange(userId, fromTimestamp, toTimestamp)
}