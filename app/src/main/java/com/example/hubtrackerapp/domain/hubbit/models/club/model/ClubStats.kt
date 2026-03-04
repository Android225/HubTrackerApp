package com.example.hubtrackerapp.domain.hubbit.models.club.model

data class ClubStats (
    val clubId: String,

    val totalCompletions: Int = 0,
    val totalChallenges: Int = 0,
    val currentChallengeId: String? = null,
    val lastUpdated: Long = System.currentTimeMillis()
)