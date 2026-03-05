package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.stats

import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class SetCurrentChallengeUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: String, challengeId: String?): Result<Unit> =
        repository.setCurrentChallenge(clubId, challengeId)
}