package com.example.hubtrackerapp.data.mapper.club

import com.example.hubtrackerapp.data.db.model.club.ClubFeedDBModel
import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubFeed

fun ClubFeed.toClubFeedDbModel(): ClubFeedDBModel {
    return ClubFeedDBModel(
        clubFeedId = clubFeedId,
        clubId = clubId,
        userId = userId,
        userName = userName,
        userImageUrl = userImageUrl,
        actionType = actionType,
        message = message,
        entityId = entityId,
        entityName = entityName,
        timestamp = timestamp
    )
}

fun ClubFeedDBModel.toClubFeedDomain(): ClubFeed {
    return ClubFeed(
        clubFeedId = clubFeedId,
        clubId = clubId,
        userId = userId,
        userName = userName,
        userImageUrl = userImageUrl,
        actionType = actionType,
        message = message,
        entityId = entityId,
        entityName = entityName,
        timestamp = timestamp
    )
}

fun List<ClubFeedDBModel>.toClubFeedDomainList(): List<ClubFeed> = this.map { it.toClubFeedDomain() }
fun List<ClubFeed>.toClubFeedDbModelList(): List<ClubFeedDBModel> = this.map { it.toClubFeedDbModel() }