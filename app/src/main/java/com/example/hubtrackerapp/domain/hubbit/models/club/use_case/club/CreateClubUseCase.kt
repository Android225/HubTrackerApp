package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club

import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import javax.inject.Inject

class CreateClubUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String,
        imageUrl: String,
        category: String,
        isPrivate: Boolean
    ): Result<Club> = repository.createClub(
        title = title,
        description = description,
        imageUrl = imageUrl,
        category = category,
        isPrivate = isPrivate
    )
}