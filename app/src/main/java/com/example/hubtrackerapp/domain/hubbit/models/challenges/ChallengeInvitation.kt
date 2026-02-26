package com.example.hubtrackerapp.domain.hubbit.models.challenges

data class ChallengeInvitation (
    val challengeId: String,
    val invitedUserId: String,
    val invitedBy: String,
    val status: InvitationStatus,
    val message: String? = null,  // личное сообщение к приглашению
    val createdAt: Long = System.currentTimeMillis(),
    val respondedAt: Long? = null
)