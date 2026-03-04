package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.get_actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetUserActionsByTypeUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        actionType: ActionType
    ): List<UserAction> = repository.getUserActionsByType(userId, actionType)
}