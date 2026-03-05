package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club

import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class UpdateClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(club: Club): Result<Club> = repository.updateClub(club)
}