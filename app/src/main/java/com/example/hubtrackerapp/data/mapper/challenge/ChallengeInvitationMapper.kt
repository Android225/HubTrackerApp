package com.example.hubtrackerapp.data.mapper.challenge

import com.example.hubtrackerapp.data.db.model.challenge.ChallengeInvitationDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeInvitation

fun ChallengeInvitation.toChallengeInvitationDbModel(): ChallengeInvitationDBModel {
    return ChallengeInvitationDBModel(
        challengeId = challengeId,
        invitedUserId = invitedUserId,
        invitedBy = invitedBy,
        status = status,
        message = message,
        createdAt = createdAt,
        respondedAt = respondedAt
    )
}

fun ChallengeInvitationDBModel.toChallengeInvitationDomain(): ChallengeInvitation {
    return ChallengeInvitation(
        challengeId = challengeId,
        invitedUserId = invitedUserId,
        invitedBy = invitedBy,
        status = status,
        message = message,
        createdAt = createdAt,
        respondedAt = respondedAt
    )
}

fun List<ChallengeInvitationDBModel>.toChallengeInvitationDomainList(): List<ChallengeInvitation> =
    this.map { it.toChallengeInvitationDomain() }
fun List<ChallengeInvitation>.toChallengeInvitationDbModelList(): List<ChallengeInvitationDBModel> =
    this.map { it.toChallengeInvitationDbModel() }