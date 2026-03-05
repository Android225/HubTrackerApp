package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club

import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class GetPopularClubsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(limit: Int = 10): List<Club> = repository.getPopularClubs(limit)
}