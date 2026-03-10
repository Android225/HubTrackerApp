package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.progress

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetUserActiveChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(userId: String): List<Challenge> =
        repository.getUserActiveChallenges(userId)
}