package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.participants

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class UpdateParticipantProgressUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String, userId: String, value: Int): Result<ChallengeParticipant> =
        repository.updateParticipantProgress(challengeId, userId, value)
}