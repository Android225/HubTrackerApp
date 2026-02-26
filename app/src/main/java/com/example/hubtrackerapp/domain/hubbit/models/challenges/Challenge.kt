package com.example.hubtrackerapp.domain.hubbit.models.challenges

import HabitMetric
import java.time.LocalDate

data class Challenge(
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

    val startDate: LocalDate,
    val endDate: LocalDate? = null,

    val isActive: Boolean = true,
    val maxParticipants: Int? = null,
    val currentParticipants: Int = 0
)