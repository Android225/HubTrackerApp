package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.progress

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class CompleteChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): Result<Unit> =
        repository.completeChallenge(challengeId)
}