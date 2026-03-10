package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.challenge

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetGlobalChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(): List<Challenge> = repository.getGlobalChallenges()
}