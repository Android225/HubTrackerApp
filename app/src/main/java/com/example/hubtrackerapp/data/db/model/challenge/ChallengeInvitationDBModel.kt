package com.example.hubtrackerapp.data.db.model.challenge

import androidx.room.Entity
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.InvitationStatus

@Entity(
    tableName = "challenge_invitations",
    primaryKeys = ["challengeId", "invitedUserId"]
)
data class ChallengeInvitationDBModel(
    val challengeId: String,
    val invitedUserId: String,
    val invitedBy: String,
    val status: InvitationStatus,
    val message: String? = null,
    val createdAt: Long,
    val respondedAt: Long? = null
)