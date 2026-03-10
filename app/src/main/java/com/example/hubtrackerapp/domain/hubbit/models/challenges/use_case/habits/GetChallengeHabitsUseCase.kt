package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.habits

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeHabit
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetChallengeHabitsUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(challengeId: String): List<ChallengeHabit> =
        repository.getChallengeHabits(challengeId)
}