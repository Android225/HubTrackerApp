package com.example.hubtrackerapp.data.db.dao.club

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hubtrackerapp.data.db.model.club.ClubDBModel

@Dao
interface ClubDao {

    @Insert
    suspend fun insertClub(club: ClubDBModel)

    @Update
    suspend fun updateClub(club: ClubDBModel)

    @Delete
    suspend fun deleteClub(club: ClubDBModel)

    @Query("SELECT * FROM clubs WHERE clubId = :clubId")
    suspend fun getClubById(clubId: String): ClubDBModel?

    @Query("SELECT * FROM clubs WHERE (:category IS NULL OR category = :category) AND (:isPrivate IS NULL OR isPrivate = :isPrivate)")
    suspend fun getClubs(
        category: String? = null,
        isPrivate: Boolean? = null
    ): List<ClubDBModel>

    @Query("SELECT * FROM clubs WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    suspend fun searchClubs(query: String): List<ClubDBModel>

    @Query("SELECT * FROM clubs ORDER BY memberCount DESC LIMIT :limit")
    suspend fun getPopularClubs(limit: Int): List<ClubDBModel>

    @Query("SELECT memberCount FROM clubs WHERE clubId = :clubId")
    suspend fun getMembersCount(clubId: String): Int

    @Query("UPDATE clubs SET memberCount = memberCount + :increment WHERE clubId = :clubId")
    suspend fun updateMemberCount(clubId: String, increment: Int)
}