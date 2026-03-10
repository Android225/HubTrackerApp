package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.participants

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class JoinChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): Result<ChallengeParticipant> =
        repository.joinChallenge(challengeId)
}