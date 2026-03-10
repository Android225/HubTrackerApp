package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.current

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class HasActiveChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(userId: String): Boolean =
        repository.hasActiveChallenge(userId)
}