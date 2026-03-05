package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.members

import com.example.hubtrackerapp.domain.hubbit.models.club.model.UserInClub
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class JoinClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: String): Result<UserInClub> = repository.joinClub(clubId)
}