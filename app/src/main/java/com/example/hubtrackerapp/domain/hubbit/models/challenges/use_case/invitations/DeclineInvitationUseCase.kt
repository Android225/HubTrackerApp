package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.invitations

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class DeclineInvitationUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String, invitedUserId: String): Result<Unit> =
        repository.declineInvitation(challengeId, invitedUserId)
}