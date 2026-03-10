package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.invitations

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeInvitation
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetIncomingInvitationsUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(userId: String): List<ChallengeInvitation> =
        repository.getIncomingInvitations(userId)
}