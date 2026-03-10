package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.participants

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class LeaveChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): Result<Unit> =
        repository.leaveChallenge(challengeId)
}