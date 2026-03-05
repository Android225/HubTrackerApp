package com.example.hubtrackerapp.domain.hubbit.models.club.use_case.feed

import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubFeed
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import javax.inject.Inject

class AddFeedEventUseCase @Inject constructor(
    private val repository: ClubRepository
) {
    suspend operator fun invoke(
        clubId: String,
        userId: String,
        userName: String,
        userImageUrl: String?,
        actionType: ActionType,
        message: String,
        entityId: String? = null,
        entityName: String? = null
    ): Result<ClubFeed> = repository.addFeedEvent(
        clubId = clubId,
        userId = userId,
        userName = userName,
        userImageUrl = userImageUrl,
        actionType = actionType,
        message = message,
        entityId = entityId,
        entityName = entityName
    )
}