package com.example.hubtrackerapp.data.db.dao.club

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hubtrackerapp.data.db.model.club.ClubStatsDBModel

@Dao
interface ClubStatsDao {

    @Insert
    suspend fun insertClubStats(clubStats: ClubStatsDBModel)

    @Update
    suspend fun updateClubStats(clubStats: ClubStatsDBModel)

    @Query("SELECT * FROM club_stats WHERE clubId = :clubId")
    suspend fun getClubStats(clubId: String): ClubStatsDBModel?

    @Query("UPDATE club_stats SET totalCompletions = totalCompletions + :completionsToAdd, totalChallenges = totalChallenges + :challengesToAdd, lastUpdated = :lastUpdated WHERE clubId = :clubId")
    suspend fun updateClubStats(
        clubId: String,
        completionsToAdd: Int,
        challengesToAdd: Int,
        lastUpdated: Long
    )

    @Query("UPDATE club_stats SET currentChallengeId = :challengeId WHERE clubId = :clubId")
    suspend fun setCurrentChallenge(clubId: String, challengeId: String?)
}