package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.progress

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengeTotalProgressUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): Int =
        repository.getChallengeTotalProgress(challengeId)
}