package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.challenge

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class DeleteChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): Result<Unit> =
        repository.deleteChallenge(challengeId)
}