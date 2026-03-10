package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.habits

import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class RemoveChallengeHabitUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String, habitId: String): Result<Unit> =
        repository.removeChallengeHabit(challengeId, habitId)
}