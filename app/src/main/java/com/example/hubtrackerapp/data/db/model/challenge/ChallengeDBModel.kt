package com.example.hubtrackerapp.data.db.model.challenge

import HabitMetric
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeGoalType
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility

@Entity(tableName = "challenges")
data class ChallengeDBModel(
    @PrimaryKey
    val challengeId: String,

    val title: String,
    val description: String,
    val createdBy: String,
    val createdAt: Long,

    val visibility: ChallengeVisibility,
    val clubId: String? = null,

    val goalType: ChallengeGoalType,

    val targetMetric: HabitMetric? = null,
    val targetValue: Int? = null,

    val startDate: Long, // LocalDate как Long
    val endDate: Long? = null,

    val isActive: Boolean = true,
    val maxParticipants: Int? = null,
    val currentParticipants: Int = 0
)