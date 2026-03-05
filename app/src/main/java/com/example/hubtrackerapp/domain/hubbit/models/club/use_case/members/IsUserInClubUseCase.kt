package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.members

import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class IsUserInClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(userId: String, clubId: String): Boolean =
        repository.isUserInClub(userId, clubId)
}