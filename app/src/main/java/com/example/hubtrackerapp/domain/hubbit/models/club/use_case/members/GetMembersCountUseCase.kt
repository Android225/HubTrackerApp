package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.members

import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class GetMembersCountUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(clubId: String): Int = repository.getMembersCount(clubId)
}