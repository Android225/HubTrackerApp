package com.example.hubtrackerapp.data.db.model.challenge

import HabitMetric
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeGoalType

@Entity(tableName = "user_current_challenge")
data class UserCurrentChallengeDBModel(
    @PrimaryKey
    val userId: String,

    val challengeId: String,

    val challengeTitle: String,
    val challengeIcon: String? = "🏆",
    val challengeColor: Int? = null,

    val startDate: Long, // LocalDate как Long
    val endDate: Long? = null,

    val goalType: ChallengeGoalType,
    val targetMetric: HabitMetric?,
    val targetValue: Int?,

    val myProgress: Int = 0,
    val myCompletions: Int = 0,

    val joinedAt: Long
)