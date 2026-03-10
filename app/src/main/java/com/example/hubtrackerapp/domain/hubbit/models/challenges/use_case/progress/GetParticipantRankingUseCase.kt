package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.progress

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetParticipantRankingUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): List<ChallengeParticipant> =
        repository.getParticipantRanking(challengeId)
}