package com.example.hubtrackerapp.data.db.dao.challenge

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hubtrackerapp.data.db.model.challenge.UserCurrentChallengeDBModel

@Dao
interface UserCurrentChallengeDao {

    @Insert
    suspend fun insertCurrentChallenge(current: UserCurrentChallengeDBModel)

    @Update
    suspend fun updateCurrentChallenge(current: UserCurrentChallengeDBModel)

    @Delete
    suspend fun deleteCurrentChallenge(current: UserCurrentChallengeDBModel)

    @Query("SELECT * FROM user_current_challenge WHERE userId = :userId")
    suspend fun getCurrentUserChallenge(userId: String): UserCurrentChallengeDBModel?

    @Query("DELETE FROM user_current_challenge WHERE userId = :userId")
    suspend fun clearCurrentUserChallenge(userId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM user_current_challenge WHERE userId = :userId)")
    suspend fun hasActiveChallenge(userId: String): Boolean

    @Query("UPDATE user_current_challenge SET myProgress = :progress, myCompletions = :completions WHERE userId = :userId")
    suspend fun updateUserProgress(
        userId: String,
        progress: Int,
        completions: Int
    )
}