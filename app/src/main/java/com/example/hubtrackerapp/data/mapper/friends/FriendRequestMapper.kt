package com.example.hubtrackerapp.data.mapper.friends

import com.example.hubtrackerapp.data.db.model.friends.FriendRequestDBModel
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest

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