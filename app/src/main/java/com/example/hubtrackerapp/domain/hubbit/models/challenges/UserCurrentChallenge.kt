package com.example.hubtrackerapp.domain.hubbit.models.challenges

import HabitMetric
import java.time.LocalDate

data class UserCurrentChallenge (
    val userId: String,
    val challengeId: String,


    val challengeTitle: String,
    val challengeIcon: String? = "🏆",  // можно передавать из создателя
    val challengeColor: Int? = null,

    // Даты
    val startDate: LocalDate,
    val endDate: LocalDate?,

    // Тип для логики подсчета
    val goalType: ChallengeGoalType,
    val targetMetric: HabitMetric?,
    val targetValue: Int?,

    // Прогресс пользователя (дублируем из ChallengeParticipants для быстрого доступа)
    val myProgress: Int = 0,
    val myCompletions: Int = 0,

    val joinedAt: Long
)