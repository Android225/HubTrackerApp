package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.challenge

import HabitMetric
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeGoalType
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import java.time.LocalDate
import javax.inject.Inject

class CreateChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        visibility: ChallengeVisibility,
        clubId: String? = null,
        goalType: ChallengeGoalType,
        targetMetric: HabitMetric? = null,
        targetValue: Int? = null,
        startDate: LocalDate,
        endDate: LocalDate? = null,
        maxParticipants: Int? = null
    ): Result<Challenge> = repository.createChallenge(
        title = title,
        description = description,
        visibility = visibility,
        clubId = clubId,
        goalType = goalType,
        targetMetric = targetMetric,
        targetValue = targetValue,
        startDate = startDate,
        endDate = endDate,
        maxParticipants = maxParticipants
    )
}