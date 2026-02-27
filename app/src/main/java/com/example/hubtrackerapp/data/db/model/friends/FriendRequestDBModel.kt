package com.example.hubtrackerapp.data.db.model.friends

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.RequestStatus

@Entity(tableName = "friend_request")
data class FriendRequestDBModel (
    @PrimaryKey
    val requestId: String,

    val fromUserId: String,
    val toUserId: String,

    val fromUserFirstName: String,
    val fromUserLastName: String,
    val fromUserImageUrl: String? = null,

    val status: RequestStatus,
    val message: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)