package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.challenge

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class UpdateChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challenge: Challenge): Result<Challenge> =
        repository.updateChallenge(challenge)
}