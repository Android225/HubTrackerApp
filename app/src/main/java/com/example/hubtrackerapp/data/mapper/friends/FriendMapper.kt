package com.example.hubtrackerapp.data.mapper.friends

import com.example.hubtrackerapp.data.db.model.friends.FriendDBModel
import com.example.hubtrackerapp.data.db.model.friends.FriendRequestDBModel
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest

fun Friend.toDbModel(): FriendDBModel {
    return FriendDBModel(
        userId = userId,
        friendId = friendId,
        friendFirstName = friendFirstName,
        friendLastName = friendLastName,
        friendImageUrl = friendImageUrl,
        points = points,
        createdAt = createdAt
    )
}
fun FriendDBModel.toEntity(): Friend{
    return Friend(
        userId = userId,
        friendId = friendId,
        friendFirstName = friendFirstName,
        friendLastName = friendLastName,
        friendImageUrl = friendImageUrl,
        points = points,
        createdAt = createdAt
    )
}