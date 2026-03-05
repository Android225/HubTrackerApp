package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.stats

import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubStats
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class UpdateClubStatsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(
        clubId: String,
        completionsToAdd: Int = 1,
        challengesToAdd: Int = 0
    ): Result<ClubStats> = repository.updateClubStats(clubId, completionsToAdd, challengesToAdd)
}