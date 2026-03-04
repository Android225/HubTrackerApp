package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ChallengeMetadata
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogChallengeStartedUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        challengeId: String,
        challengeName: String,
        challengeData: ChallengeMetadata? = null
    ): Result<UserAction> = repository.logChallengeStarted(
        challengeId = challengeId,
        challengeName = challengeName,
        challengeData = challengeData
    )
}