package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.feed

import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubFeed
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class GetClubFeedUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(
        clubId: String,
        limit: Int = 50,
        offset: Long = 0
    ): List<ClubFeed> = repository.getClubFeed(clubId, limit, offset)
}