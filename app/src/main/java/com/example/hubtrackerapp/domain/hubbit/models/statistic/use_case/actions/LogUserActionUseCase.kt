package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogUserActionUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(action: UserAction): Result<UserAction> =
        repository.logAction(action)
}