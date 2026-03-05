package com.example.hubtrackerapp.data.db.dao.challenge

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeInvitationDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.InvitationStatus

@Dao
interface ChallengeInvitationDao {

    @Insert
    suspend fun insertInvitation(invitation: ChallengeInvitationDBModel)

    @Update
    suspend fun updateInvitation(invitation: ChallengeInvitationDBModel)

    @Delete
    suspend fun deleteInvitation(invitation: ChallengeInvitationDBModel)

    @Query("SELECT * FROM challenge_invitations WHERE challengeId = :challengeId AND invitedUserId = :invitedUserId")
    suspend fun getInvitation(challengeId: String, invitedUserId: String): ChallengeInvitationDBModel?

    @Query("SELECT * FROM challenge_invitations WHERE invitedUserId = :userId AND status = 'PENDING'")
    suspend fun getIncomingInvitations(userId: String): List<ChallengeInvitationDBModel>

    @Query("SELECT * FROM challenge_invitations WHERE invitedBy = :userId AND status = 'PENDING'")
    suspend fun getOutgoingInvitations(userId: String): List<ChallengeInvitationDBModel>

    @Query("UPDATE challenge_invitations SET status = :status, respondedAt = :respondedAt WHERE challengeId = :challengeId AND invitedUserId = :invitedUserId")
    suspend fun updateInvitationStatus(
        challengeId: String,
        invitedUserId: String,
        status: InvitationStatus,
        respondedAt: Long
    )

    @Query("DELETE FROM challenge_invitations WHERE challengeId = :challengeId")
    suspend fun deleteAllInvitations(challengeId: String)
}