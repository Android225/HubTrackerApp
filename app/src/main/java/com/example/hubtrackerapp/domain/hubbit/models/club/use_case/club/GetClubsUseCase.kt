package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club

import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class GetClubsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(
        category: String? = null,
        isPrivate: Boolean? = null
    ): List<Club> = repository.getClubs(category, isPrivate)
}