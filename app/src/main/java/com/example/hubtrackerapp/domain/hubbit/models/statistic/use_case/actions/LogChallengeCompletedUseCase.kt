package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogChallengeCompletedUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        challengeId: String,
        challengeName: String,
        pointsEarned: Int
    ): Result<UserAction> = repository.logChallengeCompleted(
        challengeId = challengeId,
        challengeName = challengeName,
        pointsEarned = pointsEarned
    )
}