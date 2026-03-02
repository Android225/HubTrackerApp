package com.example.hubtrackerapp.domain.hubbit.models.statistic.model

data class ChallengeMetadata(
    val challengeType: String,
    val targetValue: Int,
    val startDate: Long,
    val endDate: Long?
)