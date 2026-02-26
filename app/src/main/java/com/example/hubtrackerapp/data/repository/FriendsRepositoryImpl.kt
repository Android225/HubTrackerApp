package com.example.hubtrackerapp.data.repository

import com.example.hubtrackerapp.data.db.dao.FriendRequestDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.domain.hubbit.models.friends.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.FriendRequest
import com.example.hubtrackerapp.domain.repository.FriendsRepository
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendRequestDao: FriendRequestDao,
    private val userDao: UserDao,
    //private val friendDao: FriendDao
) : FriendsRepository {
    override suspend fun sendFriendRequest(
        toUserId: String,
        message: String?
    ): Result<FriendRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun getIncomingRequests(): List<FriendRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun getOutgoingRequests(): List<FriendRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun acceptFriendRequest(requestId: String): Result<Friend> {
        TODO("Not yet implemented")
    }

    override suspend fun declineFriendRequest(requestId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun cancelFriendRequest(requestId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getRequestById(requestId: String): FriendRequest? {
        TODO("Not yet implemented")
    }

    override suspend fun canSendRequest(toUserId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getAllRequestsForUser(): List<FriendRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRequest(requestId: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getFriends(): List<Friend> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFriend(friendId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun areFriends(
        userId: String,
        otherUserId: String
    ): Boolean {
        TODO("Not yet implemented")
    }
}