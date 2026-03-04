package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.get_actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetUserActionsByTypeInRangeUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        actionType: ActionType,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserAction> = repository.getUserActionsByTypeInRange(
        userId = userId,
        actionType = actionType,
        fromTimestamp = fromTimestamp,
        toTimestamp = toTimestamp
    )
}