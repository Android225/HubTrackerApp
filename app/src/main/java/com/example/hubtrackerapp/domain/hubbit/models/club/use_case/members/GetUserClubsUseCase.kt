package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.members

import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class GetUserClubsUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(userId: String): List<Club> = repository.getUserClubs(userId)
}