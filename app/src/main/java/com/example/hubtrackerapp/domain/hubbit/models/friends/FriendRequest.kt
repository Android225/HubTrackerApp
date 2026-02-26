package com.example.hubtrackerapp.domain.hubbit.models.friends

data class FriendRequest (
    val fromUserId: String,  // кто отправил
    val toUserId: String,    // кому отправили

    val fromUserFirstName: String,
    val fromUserLastName: String,
    val fromUserImageUrl: String? = null,

    val status: RequestStatus,
    val message: String? = null,
    val createdAt: Long,
    val updatedAt: Long
)