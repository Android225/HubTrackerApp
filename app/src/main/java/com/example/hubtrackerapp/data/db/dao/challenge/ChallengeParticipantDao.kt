package com.example.hubtrackerapp.data.db.dao.challenge

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeParticipantDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ParticipantStatus

@Dao
interface ChallengeParticipantDao {

    @Insert
    suspend fun insertParticipant(participant: ChallengeParticipantDBModel)

    @Update
    suspend fun updateParticipant(participant: ChallengeParticipantDBModel)

    @Delete
    suspend fun deleteParticipant(participant: ChallengeParticipantDBModel)

    @Query("SELECT * FROM challenge_participants WHERE challengeId = :challengeId")
    suspend fun getChallengeParticipants(challengeId: String): List<ChallengeParticipantDBModel>

    @Query("SELECT * FROM challenge_participants WHERE challengeId = :challengeId AND userId = :userId")
    suspend fun getParticipant(challengeId: String, userId: String): ChallengeParticipantDBModel?

    @Query("SELECT EXISTS(SELECT 1 FROM challenge_participants WHERE challengeId = :challengeId AND userId = :userId)")
    suspend fun isUserParticipating(challengeId: String, userId: String): Boolean

    @Query("UPDATE challenge_participants SET totalValue = totalValue + :value, completionsCount = completionsCount + 1, lastContribution = :lastContribution, updatedAt = :updatedAt WHERE challengeId = :challengeId AND userId = :userId")
    suspend fun updateParticipantProgress(
        challengeId: String,
        userId: String,
        value: Int,
        lastContribution: Long,
        updatedAt: Long
    )

    @Query("UPDATE challenge_participants SET personalContribution = personalContribution + :value WHERE challengeId = :challengeId AND userId = :userId")
    suspend fun updatePersonalContribution(
        challengeId: String,
        userId: String,
        value: Int
    )

    @Query("SELECT SUM(totalValue) FROM challenge_participants WHERE challengeId = :challengeId")
    suspend fun getChallengeTotalProgress(challengeId: String): Int?

    @Query("SELECT * FROM challenge_participants WHERE challengeId = :challengeId ORDER BY totalValue DESC")
    suspend fun getParticipantRanking(challengeId: String): List<ChallengeParticipantDBModel>

    @Query("UPDATE challenge_participants SET status = :status, updatedAt = :updatedAt WHERE challengeId = :challengeId AND userId = :userId")
    suspend fun updateParticipantStatus(
        challengeId: String,
        userId: String,
        status: ParticipantStatus,
        updatedAt: Long
    )

    @Query("SELECT * FROM challenge_participants WHERE userId = :userId")
    suspend fun getUserParticipations(userId: String): List<ChallengeParticipantDBModel>

    @Query("DELETE FROM challenge_participants WHERE challengeId = :challengeId")
    suspend fun deleteAllParticipants(challengeId: String)
}