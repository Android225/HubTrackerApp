package com.example.hubtrackerapp.data.db.dao.club

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.club.ClubFeedDBModel

@Dao
interface ClubFeedDao {

    @Insert
    suspend fun insertFeedEvent(event: ClubFeedDBModel)

    @Query("""
        SELECT * FROM club_feed 
        WHERE clubId = :clubId 
        ORDER BY timestamp DESC 
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getClubFeed(
        clubId: String,
        limit: Int,
        offset: Long
    ): List<ClubFeedDBModel>

    @Query("""
        SELECT * FROM club_feed 
        WHERE clubId = :clubId 
        AND timestamp BETWEEN :fromTimestamp AND :toTimestamp
        ORDER BY timestamp DESC
    """)
    suspend fun getClubFeedForPeriod(
        clubId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<ClubFeedDBModel>

    @Query("DELETE FROM club_feed WHERE clubId = :clubId AND timestamp < :beforeTimestamp")
    suspend fun deleteOldFeedEvents(clubId: String, beforeTimestamp: Long)
}