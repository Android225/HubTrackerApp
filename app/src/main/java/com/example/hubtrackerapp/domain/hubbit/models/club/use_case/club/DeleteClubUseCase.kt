package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club

import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class DeleteClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: String): Result<Unit> = repository.deleteClub(clubId)
}