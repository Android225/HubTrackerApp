package com.example.hubtrackerapp.data.db.dao.challenge

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility

@Dao
interface ChallengeDao {
    @Insert
    suspend fun insertChallenge(challenge: ChallengeDBModel)

    @Update
    suspend fun updateChallenge(challenge: ChallengeDBModel)

    @Delete
    suspend fun deleteChallenge(challenge: ChallengeDBModel)

    @Query("SELECT * FROM challenges WHERE challengeId = :challengeId")
    suspend fun getChallengeById(challengeId: String): ChallengeDBModel?

    @Query("SELECT * FROM challenges WHERE (:visibility IS NULL OR visibility = :visibility) AND (:clubId IS NULL OR clubId = :clubId) AND (:isActive IS NULL OR isActive = :isActive)")
    suspend fun getChallenges(
        visibility: ChallengeVisibility? = null,
        clubId: String? = null,
        isActive: Boolean? = null
    ): List<ChallengeDBModel>

    @Query("SELECT * FROM challenges WHERE visibility = 'GLOBAL'")
    suspend fun getGlobalChallenges(): List<ChallengeDBModel>

    @Query("SELECT * FROM challenges WHERE clubId = :clubId")
    suspend fun getClubChallenges(clubId: String): List<ChallengeDBModel>

    @Query("SELECT * FROM challenges WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    suspend fun searchChallenges(query: String): List<ChallengeDBModel>

    @Query("SELECT * FROM challenges ORDER BY currentParticipants DESC LIMIT :limit")
    suspend fun getPopularChallenges(limit: Int): List<ChallengeDBModel>

    @Query("UPDATE challenges SET currentParticipants = currentParticipants + 1 WHERE challengeId = :challengeId")
    suspend fun incrementParticipants(challengeId: String)

    @Query("UPDATE challenges SET currentParticipants = currentParticipants - 1 WHERE challengeId = :challengeId")
    suspend fun decrementParticipants(challengeId: String)

    @Query("UPDATE challenges SET isActive = 0 WHERE challengeId = :challengeId")
    suspend fun deactivateChallenge(challengeId: String)
}