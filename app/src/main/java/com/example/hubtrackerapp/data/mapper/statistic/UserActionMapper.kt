package com.example.hubtrackerapp.data.mapper.statistic

import com.example.hubtrackerapp.data.db.model.statistic.UserActionDBModel
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction

fun UserAction.toUserActionDbModel(): UserActionDBModel {
    return UserActionDBModel(
        userActionId = userActionId,
        userId = userId,
        actionType = actionType,
        entityId = entityId,
        entityName = entityName,
        pointsEarned = pointEarned,
        timestamp = timeStamp,
        additionalData = additionalData
    )
}

fun UserActionDBModel.toUserActionEntity(): UserAction{
    return UserAction(
        userActionId = userActionId,
        userId = userId,
        actionType = actionType,
        entityId = entityId,
        entityName = entityName,
        pointEarned = pointsEarned,
        timeStamp = timestamp,
        additionalData = additionalData
    )
}