package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.search

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class SearchChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(query: String): List<Challenge> =
        repository.searchChallenges(query)
}