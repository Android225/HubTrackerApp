package com.example.hubtrackerapp.domain.hubbit.models.challenges.use_case.current

import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.UserCurrentChallenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import javax.inject.Inject

class GetCurrentUserChallengeUseCase @Inject constructor(
    private val repository: ChallengeRepository
) {
    suspend operator fun invoke(userId: String): UserCurrentChallenge? =
        repository.getCurrentUserChallenge(userId)
}