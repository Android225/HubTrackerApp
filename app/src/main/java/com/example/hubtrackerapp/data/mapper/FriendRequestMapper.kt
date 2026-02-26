package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.FriendRequestDBModel
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.friends.FriendRequest

fun FriendRequest.toDbModel(): FriendRequestDBModel {
    return FriendRequestDBModel(
        requestId = requestId,
        fromUserId = fromUserId,
        toUserId = toUserId,
        fromUserFirstName = fromUserFirstName,
        fromUserLastName = fromUserLastName,
        fromUserImageUrl = fromUserImageUrl,
        status = status,
        message = message,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
fun FriendRequestDBModel.toEntity(): FriendRequest{
    return FriendRequest(
        requestId = requestId,
        fromUserId = fromUserId,
        toUserId = toUserId,
        fromUserFirstName = fromUserFirstName,
        fromUserLastName = fromUserLastName,
        fromUserImageUrl = fromUserImageUrl,
        status = status,
        message = message,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}