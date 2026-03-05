package com.example.hubtrackerapp.domain.hubbit.models.challenges.model

import HabitMetric

data class ChallengeHabit (
    val challengeId: String,
    val habitId: String,
    val habitName: String,
    val habitIcon: String,
    val habitColor: Int,
    val metric: HabitMetric,
    val target: String,
    val scheduleType: String,
    val scheduleValue: String?,
    val activeFromDate: Long,
    val activeToDate: Long?,
    val createdAt: Long
)