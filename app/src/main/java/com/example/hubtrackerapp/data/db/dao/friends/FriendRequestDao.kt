package com.example.hubtrackerapp.data.db.dao.friends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.friends.FriendRequestDBModel
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.RequestStatus

@Dao
interface FriendRequestDao {

    @Insert
    suspend fun insertRequest(request: FriendRequestDBModel)

    @Query("""
        SELECT * FROM friend_request 
        WHERE toUserId = :currentUserId 
        AND status = 'PENDING'
        ORDER BY createdAt DESC
    """)
    suspend fun getIncomingRequests(currentUserId: String): List<FriendRequestDBModel>

    @Query("""
        SELECT * FROM friend_request 
        WHERE fromUserId = :currentUserId 
        AND status = 'PENDING'
        ORDER BY createdAt DESC
    """)
    suspend fun getOutgoingRequests(currentUserId: String): List<FriendRequestDBModel>

    //для изменения статуса заявки
    @Query("""
        UPDATE friend_request 
        SET status = :status, updatedAt = :updatedAt
        WHERE requestId = :requestId
    """)
    suspend fun updateRequestStatus(requestId: String, status: RequestStatus, updatedAt: Long)

    @Query("SELECT * FROM friend_request WHERE requestId = :requestId")
    suspend fun getRequestById(requestId: String): FriendRequestDBModel?

    @Query("""
        SELECT * FROM friend_request 
        WHERE fromUserId = :fromUserId 
        AND toUserId = :toUserId 
        AND status = 'PENDING'
        LIMIT 1
    """)
    suspend fun getPendingRequestBetweenUsers(
        fromUserId: String,
        toUserId: String
    ): FriendRequestDBModel?

    @Query("""
        SELECT * FROM friend_request 
        WHERE fromUserId = :userId OR toUserId = :userId
        ORDER BY createdAt DESC
    """)
    suspend fun getAllUserRequests(userId: String): List<FriendRequestDBModel>

//    @Query("""
//    SELECT EXISTS(
//        SELECT 1 FROM friend_request
//        WHERE ((fromUserId = :userId1 AND toUserId = :userId2)
//               OR (fromUserId = :userId2 AND toUserId = :userId1))
//        AND status = 'PENDING'
//    )
//""")
//    suspend fun hasPendingRequest(userId1: String, userId2: String): Boolean

    @Query("DELETE FROM friend_request WHERE requestId = :requestId")
    suspend fun deleteRequestById(requestId: String)
}