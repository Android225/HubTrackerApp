package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogActionUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        actionType: ActionType,
        entityId: String? = null,
        entityName: String? = null,
        pointsEarned: Int = 0,
        additionalData: String? = null
    ): Result<UserAction> = repository.logAction(
        actionType = actionType,
        entityId = entityId,
        entityName = entityName,
        pointsEarned = pointsEarned,
        additionalData = additionalData
    )
}