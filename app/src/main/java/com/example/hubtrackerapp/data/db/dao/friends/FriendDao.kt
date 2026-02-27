package com.example.hubtrackerapp.data.db.dao.friends

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.friends.FriendDBModel
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.Friend

@Dao
interface FriendDao {
    @Query("""
        SELECT * FROM friend 
        WHERE userId = :userId 
        ORDER BY friendFirstName ASC
    """)
    suspend fun getFriends(userId: String): List<FriendDBModel>

    @Query("""
        DELETE FROM friend 
        WHERE userId = :userId AND friendId = :friendId
    """)
    suspend fun removeFriend(userId: String, friendId: String)

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM friend
            WHERE userId = :userId AND friendId = :otherUserId
        )
    """)
    suspend fun areFriends(userId: String, otherUserId: String): Boolean

    @Insert
    suspend fun insert(friend: FriendDBModel)

    @Query("SELECT COUNT(*) FROM friend WHERE userId = :userId")
    suspend fun getFriendsCount(userId: String): Int
}