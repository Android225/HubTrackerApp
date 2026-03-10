package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.invitations

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class AcceptInvitationUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String, invitedUserId: String): Result<ChallengeParticipant> =
        repository.acceptInvitation(challengeId, invitedUserId)
}