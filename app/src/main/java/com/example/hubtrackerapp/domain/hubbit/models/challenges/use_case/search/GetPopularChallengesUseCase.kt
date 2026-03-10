package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.search

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetPopularChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(limit: Int = 10): List<Challenge> =
        repository.getPopularChallenges(limit)
}