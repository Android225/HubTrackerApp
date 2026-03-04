package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.get_actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class GetAllUserActionsUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(userId: String): List<UserAction> =
        repository.getAllUserActions(userId)
}