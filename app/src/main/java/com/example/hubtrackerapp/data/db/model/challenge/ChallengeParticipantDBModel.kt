package com.example.hubtrackerapp.data.db.model.challenge

import androidx.room.Entity
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ParticipantStatus

@Entity(
    tableName = "challenge_participants",
    primaryKeys = ["challengeId", "userId"]
)
data class ChallengeParticipantDBModel(
    val challengeId: String,
    val userId: String,

    val status: ParticipantStatus,
    val joinedAt: Long? = null,

    val totalValue: Int = 0,
    val completionsCount: Int = 0,
    val lastContribution: Long? = null,

    val personalContribution: Int = 0,

    val rank: Int? = null,
    val isWinner: Boolean = false,

    val updatedAt: Long
)