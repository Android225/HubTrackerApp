package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.challenge

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengesUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(
        visibility: ChallengeVisibility? = null,
        clubId: String? = null,
        isActive: Boolean? = null
    ): List<Challenge> = repository.getChallenges(visibility, clubId, isActive)
}