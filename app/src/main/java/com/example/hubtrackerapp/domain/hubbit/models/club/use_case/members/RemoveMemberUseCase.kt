package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.members

import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class RemoveMemberUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: String, userId: String): Result<Unit> =
        repository.removeMember(clubId, userId)
}