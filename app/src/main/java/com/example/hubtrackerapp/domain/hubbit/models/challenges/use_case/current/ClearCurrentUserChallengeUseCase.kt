package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.current

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class ClearCurrentUserChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(userId: String): Result<Unit> =
        repository.clearCurrentUserChallenge(userId)
}