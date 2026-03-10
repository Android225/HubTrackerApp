package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.habits

import HabitMetric
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeHabit
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class AddChallengeHabitUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(
        challengeId: String,
        habitId: String,
        habitName: String,
        habitIcon: String,
        habitColor: Int,
        metric: HabitMetric,
        target: String,
        scheduleType: String,
        scheduleValue: String?
    ): Result<ChallengeHabit> = repository.addChallengeHabit(
        challengeId = challengeId,
        habitId = habitId,
        habitName = habitName,
        habitIcon = habitIcon,
        habitColor = habitColor,
        metric = metric,
        target = target,
        scheduleType = scheduleType,
        scheduleValue = scheduleValue
    )
}