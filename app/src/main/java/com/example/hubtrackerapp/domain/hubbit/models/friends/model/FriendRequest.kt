package com.example.hubtrackerapp.domain.hubbit.models.friends.model

data class FriendRequest (
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