package com.example.hubtrackerapp.data.repository

import com.example.hubtrackerapp.data.db.dao.friends.FriendRequestDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.dao.friends.FriendDao
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest
import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendRequestDao: FriendRequestDao,
    private val friendDao: FriendDao,
    private val userDao: UserDao
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

    override suspend fun removeFriend(userId: String,friendId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun areFriends(
        userId: String,
        otherUserId: String
    ): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getFriendsCount(userId: String): Int {
        TODO("Not yet implemented")
    }
}