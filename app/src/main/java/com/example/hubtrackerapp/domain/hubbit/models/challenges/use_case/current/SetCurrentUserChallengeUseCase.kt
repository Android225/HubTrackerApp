package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.current

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class SetCurrentUserChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(
        userId: String,
        challenge: Challenge,
        participant: ChallengeParticipant
    ): Result<Unit> = repository.setCurrentUserChallenge(userId, challenge, participant)
}