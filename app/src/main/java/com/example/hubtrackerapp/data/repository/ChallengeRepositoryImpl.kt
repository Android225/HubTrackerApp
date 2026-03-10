package com.example.hubtrackerapp.data.repository

import HabitMetric
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeHabitDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeInvitationDao
import com.example.hubtrackerapp.data.db.dao.challenge.ChallengeParticipantDao
import com.example.hubtrackerapp.data.db.dao.challenge.UserCurrentChallengeDao
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeDBModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeHabitDBModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeInvitationDBModel
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeParticipantDBModel
import com.example.hubtrackerapp.data.db.model.challenge.UserCurrentChallengeDBModel
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeDbModel
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeDomain
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeDomainList
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeHabitDomain
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeHabitDomainList
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeInvitationDomain
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeInvitationDomainList
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeParticipantDomain
import com.example.hubtrackerapp.data.mapper.challenge.toChallengeParticipantDomainList
import com.example.hubtrackerapp.data.mapper.challenge.toUserCurrentChallengeDomain
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeGoalType
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeHabit
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeInvitation
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.InvitationStatus
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ParticipantStatus
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.UserCurrentChallenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.repository.ChallengeRepository
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeDao: ChallengeDao,
    private val challengeHabitDao: ChallengeHabitDao,
    private val challengeInvitationDao: ChallengeInvitationDao,
    private val challengeParticipantDao: ChallengeParticipantDao,
    private val userCurrentChallengeDao: UserCurrentChallengeDao,
    private val userDao: UserDao
) : ChallengeRepository {

    private suspend fun getCurrentUserId(): String {
        return userDao.getUserId() ?: throw IllegalStateException("User not logged in")
    }

    private fun LocalDate.toEpochDayMillis(): Long =
        this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    //---- основыне операции

    override suspend fun createChallenge(
        title: String,
        description: String,
        visibility: ChallengeVisibility,
        clubId: String?,
        goalType: ChallengeGoalType,
        targetMetric: HabitMetric?,
        targetValue: Int?,
        startDate: LocalDate,
        endDate: LocalDate?,
        maxParticipants: Int?
    ): Result<Challenge> = try {
        val currentUserId = getCurrentUserId()
        val now = System.currentTimeMillis()

        val challenge = ChallengeDBModel(
            challengeId = UUID.randomUUID().toString(),
            title = title,
            description = description,
            createdBy = currentUserId,
            createdAt = now,
            visibility = visibility,
            clubId = clubId,
            goalType = goalType,
            targetMetric = targetMetric,
            targetValue = targetValue,
            startDate = startDate.toEpochDayMillis(),
            endDate = endDate?.toEpochDayMillis(),
            isActive = true,
            maxParticipants = maxParticipants,
            currentParticipants = 0
        )

        challengeDao.insertChallenge(challenge)

        // Если челлендж создан в клубе, уведомление в ленту будет позже
        // Если создатель сразу присоединяется - нужно будет вызвать joinChallenge отдельно

        Result.success(challenge.toChallengeDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getChallengeById(challengeId: String): Challenge? {
        return challengeDao.getChallengeById(challengeId)?.toChallengeDomain()
    }

    override suspend fun getChallenges(
        visibility: ChallengeVisibility?,
        clubId: String?,
        isActive: Boolean?
    ): List<Challenge> {
        return challengeDao.getChallenges(visibility, clubId, isActive).toChallengeDomainList()
    }

    override suspend fun getGlobalChallenges(): List<Challenge> {
        return challengeDao.getGlobalChallenges().toChallengeDomainList()
    }

    override suspend fun getClubChallenges(clubId: String): List<Challenge> {
        return challengeDao.getClubChallenges(clubId).toChallengeDomainList()
    }

    override suspend fun updateChallenge(challenge: Challenge): Result<Challenge> {
        return try {
            val existing = challengeDao.getChallengeById(challenge.challengeId)
                ?: return Result.failure(Exception("Challenge not found"))

            val currentUserId = getCurrentUserId()
            if (existing.createdBy != currentUserId) {
                return Result.failure(Exception("Only creator can update challenge"))
            }

            val updatedChallenge = challenge.copy(
                createdAt = existing.createdAt,
                createdBy = existing.createdBy
            )

            challengeDao.updateChallenge(updatedChallenge.toChallengeDbModel())
            Result.success(updatedChallenge)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteChallenge(challengeId: String): Result<Unit> {
        return try {
            val challenge = challengeDao.getChallengeById(challengeId)
                ?: return Result.failure(Exception("Challenge not found"))

            val currentUserId = getCurrentUserId()
            if (challenge.createdBy != currentUserId) {
                return Result.failure(Exception("Only creator can delete challenge"))
            }

            // Удаляем связанные данные
            challengeHabitDao.deleteAllChallengeHabits(challengeId)
            challengeParticipantDao.deleteAllParticipants(challengeId)
            challengeInvitationDao.deleteAllInvitations(challengeId)

            // Удаляем сам челлендж
            challengeDao.deleteChallenge(challenge)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // -- habits для челенджей

    override suspend fun addChallengeHabit(
        challengeId: String,
        habitId: String,
        habitName: String,
        habitIcon: String,
        habitColor: Int,
        metric: HabitMetric,
        target: String,
        scheduleType: String,
        scheduleValue: String?
    ): Result<ChallengeHabit> {
        return try {
            val challenge = challengeDao.getChallengeById(challengeId)
                ?: return Result.failure(Exception("Challenge not found"))

            val currentUserId = getCurrentUserId()
            if (challenge.createdBy != currentUserId) {
                return Result.failure(Exception("Only creator can add habits"))
            }

            val now = System.currentTimeMillis()
            val challengeHabit = ChallengeHabitDBModel(
                challengeId = challengeId,
                habitId = habitId,
                habitName = habitName,
                habitIcon = habitIcon,
                habitColor = habitColor,
                metric = metric,
                target = target,
                scheduleType = scheduleType,
                scheduleValue = scheduleValue,
                activeFromDate = challenge.startDate,
                activeToDate = challenge.endDate,
                createdAt = now
            )

            challengeHabitDao.insertChallengeHabit(challengeHabit)
            Result.success(challengeHabit.toChallengeHabitDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getChallengeHabits(challengeId: String): List<ChallengeHabit> {
        return challengeHabitDao.getChallengeHabits(challengeId).toChallengeHabitDomainList()
    }

    override suspend fun removeChallengeHabit(
        challengeId: String,
        habitId: String
    ): Result<Unit> {
        return try {
            val challenge = challengeDao.getChallengeById(challengeId)
                ?: return Result.failure(Exception("Challenge not found"))

            val currentUserId = getCurrentUserId()
            if (challenge.createdBy != currentUserId) {
                return Result.failure(Exception("Only creator can remove habits"))
            }

            challengeHabitDao.deleteChallengeHabit(challengeId, habitId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //---- участники

    override suspend fun joinChallenge(challengeId: String): Result<ChallengeParticipant> {
        return try {
            val currentUserId = getCurrentUserId()
            val now = System.currentTimeMillis()

            val challenge = challengeDao.getChallengeById(challengeId)
                ?: return Result.failure(Exception("Challenge not found"))

            if (!challenge.isActive) {
                return Result.failure(Exception("Challenge is not active"))
            }

            if (challenge.maxParticipants != null && challenge.currentParticipants >= challenge.maxParticipants) {
                return Result.failure(Exception("Challenge is full"))
            }

            if (challengeParticipantDao.isUserParticipating(challengeId, currentUserId)) {
                return Result.failure(Exception("Already participating"))
            }

            // Проверяем, если челлендж приватный - нужно приглашение
            if (challenge.visibility == ChallengeVisibility.PRIVATE) {
                val invitation = challengeInvitationDao.getInvitation(challengeId, currentUserId)
                if (invitation == null || invitation.status != InvitationStatus.PENDING) {
                    return Result.failure(Exception("No pending invitation"))
                }
                // Удаляем приглашение после использования
                challengeInvitationDao.deleteInvitation(invitation)
            }

            val participant = ChallengeParticipantDBModel(
                challengeId = challengeId,
                userId = currentUserId,
                status = ParticipantStatus.JOINED,
                joinedAt = now,
                updatedAt = now
            )

            challengeParticipantDao.insertParticipant(participant)
            challengeDao.incrementParticipants(challengeId)

            // Если у пользователя нет активного челленджа - устанавливаем этот
            if (!userCurrentChallengeDao.hasActiveChallenge(currentUserId)) {
                val challengeDomain = challenge.toChallengeDomain()
                val participantDomain = participant.toChallengeParticipantDomain()
                setCurrentUserChallenge(currentUserId, challengeDomain, participantDomain)
            }

            Result.success(participant.toChallengeParticipantDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun leaveChallenge(challengeId: String): Result<Unit> = try {
        val currentUserId = getCurrentUserId()

        val participant = challengeParticipantDao.getParticipant(challengeId, currentUserId)
            ?: return Result.failure(Exception("Not participating"))

        challengeParticipantDao.deleteParticipant(participant)
        challengeDao.decrementParticipants(challengeId)

        // Если это был текущий активный челлендж - очищаем
        val current = userCurrentChallengeDao.getCurrentUserChallenge(currentUserId)
        if (current?.challengeId == challengeId) {
            userCurrentChallengeDao.clearCurrentUserChallenge(currentUserId)
        }

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getChallengeParticipants(challengeId: String): List<ChallengeParticipant> {
        return challengeParticipantDao.getChallengeParticipants(challengeId).toChallengeParticipantDomainList()
    }

    override suspend fun getParticipantProgress(
        challengeId: String,
        userId: String
    ): ChallengeParticipant? {
        return challengeParticipantDao.getParticipant(challengeId, userId)?.toChallengeParticipantDomain()
    }

    override suspend fun updateParticipantProgress(
        challengeId: String,
        userId: String,
        value: Int
    ): Result<ChallengeParticipant> = try {
        val now = System.currentTimeMillis()

        challengeParticipantDao.updateParticipantProgress(
            challengeId = challengeId,
            userId = userId,
            value = value,
            lastContribution = now,
            updatedAt = now
        )

        challengeParticipantDao.updatePersonalContribution(challengeId, userId, value)

        val updated = challengeParticipantDao.getParticipant(challengeId, userId)
            ?: return Result.failure(Exception("Participant not found"))

        // Обновляем прогресс в текущем челлендже, если это он
        val current = userCurrentChallengeDao.getCurrentUserChallenge(userId)
        if (current?.challengeId == challengeId) {
            userCurrentChallengeDao.updateUserProgress(
                userId = userId,
                progress = updated.totalValue,
                completions = updated.completionsCount
            )
        }

        Result.success(updated.toChallengeParticipantDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun isUserParticipating(
        challengeId: String,
        userId: String
    ): Boolean {
        return challengeParticipantDao.isUserParticipating(challengeId, userId)
    }

    //----- приглашения в челендж

    override suspend fun sendInvitation(
        challengeId: String,
        invitedUserId: String,
        message: String?
    ): Result<ChallengeInvitation> {
        return try {
            val currentUserId = getCurrentUserId()
            val now = System.currentTimeMillis()

            val challenge = challengeDao.getChallengeById(challengeId)
                ?: return Result.failure(Exception("Challenge not found"))

            if (challenge.createdBy != currentUserId) {
                return Result.failure(Exception("Only creator can send invitations"))
            }

            if (challenge.visibility != ChallengeVisibility.PRIVATE) {
                return Result.failure(Exception("Invitations only for private challenges"))
            }

            val invitation = ChallengeInvitationDBModel(
                challengeId = challengeId,
                invitedUserId = invitedUserId,
                invitedBy = currentUserId,
                status = InvitationStatus.PENDING,
                message = message,
                createdAt = now,
                respondedAt = null
            )

            challengeInvitationDao.insertInvitation(invitation)
            Result.success(invitation.toChallengeInvitationDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun acceptInvitation(
        challengeId: String,
        invitedUserId: String
    ): Result<ChallengeParticipant> {
        return try {
            val currentUserId = getCurrentUserId()

            if (invitedUserId != currentUserId) {
                return Result.failure(Exception("This invitation is for another user"))
            }

            val invitation = challengeInvitationDao.getInvitation(challengeId, invitedUserId)
                ?: return Result.failure(Exception("Invitation not found"))

            if (invitation.status != InvitationStatus.PENDING) {
                return Result.failure(Exception("Invitation already processed"))
            }

            val result = joinChallenge(challengeId)

            if (result.isSuccess) {
                challengeInvitationDao.deleteInvitation(invitation)
            }

            result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun declineInvitation(
        challengeId: String,
        invitedUserId: String
    ): Result<Unit> = try {
        challengeInvitationDao.updateInvitationStatus(
            challengeId = challengeId,
            invitedUserId = invitedUserId,
            status = InvitationStatus.DECLINED,
            respondedAt = System.currentTimeMillis()
        )
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getIncomingInvitations(userId: String): List<ChallengeInvitation> {
        return challengeInvitationDao.getIncomingInvitations(userId)
            .toChallengeInvitationDomainList()
    }

    override suspend fun getOutgoingInvitations(userId: String): List<ChallengeInvitation> {
        return challengeInvitationDao.getOutgoingInvitations(userId)
            .toChallengeInvitationDomainList()
    }

    //----- текущщий челендж пользователя

    override suspend fun getCurrentUserChallenge(userId: String): UserCurrentChallenge? {
        return userCurrentChallengeDao.getCurrentUserChallenge(userId)?.toUserCurrentChallengeDomain()
    }

    override suspend fun setCurrentUserChallenge(
        userId: String,
        challenge: Challenge,
        participant: ChallengeParticipant
    ): Result<Unit> {
        return try {
            // Проверяем, нет ли уже активного
            if (userCurrentChallengeDao.hasActiveChallenge(userId)) {
                return Result.failure(Exception("User already has an active challenge"))
            }

            val current = UserCurrentChallengeDBModel(
                userId = userId,
                challengeId = challenge.challengeId,
                challengeTitle = challenge.title,
                challengeIcon = "🏆", // TODO: мб сделать передачу иконок?
                challengeColor = null,
                startDate = challenge.startDate.toEpochDayMillis(),
                endDate = challenge.endDate?.toEpochDayMillis(),
                goalType = challenge.goalType,
                targetMetric = challenge.targetMetric,
                targetValue = challenge.targetValue,
                myProgress = participant.totalValue,
                myCompletions = participant.completionsCount,
                joinedAt = participant.joinedAt ?: System.currentTimeMillis()
            )

            userCurrentChallengeDao.insertCurrentChallenge(current)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun clearCurrentUserChallenge(userId: String): Result<Unit> = try {
        userCurrentChallengeDao.clearCurrentUserChallenge(userId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun hasActiveChallenge(userId: String): Boolean {
        return userCurrentChallengeDao.hasActiveChallenge(userId)
    }

    //------ прогресс и статистика
    override suspend fun getChallengeTotalProgress(challengeId: String): Int {
        return challengeParticipantDao.getChallengeTotalProgress(challengeId) ?: 0
    }

    override suspend fun getParticipantRanking(challengeId: String): List<ChallengeParticipant> {
        return challengeParticipantDao.getParticipantRanking(challengeId)
            .toChallengeParticipantDomainList()
    }

    override suspend fun completeChallenge(challengeId: String): Result<Unit> = try {
        val challenge = challengeDao.getChallengeById(challengeId)
            ?: return Result.failure(Exception("Challenge not found"))

        // Обновляем статус челленджа
        challengeDao.deactivateChallenge(challengeId)

        // Обновляем статус всех участников
        val participants = challengeParticipantDao.getChallengeParticipants(challengeId)
        participants.forEach { participant ->
            challengeParticipantDao.updateParticipantStatus(
                challengeId = challengeId,
                userId = participant.userId,
                status = ParticipantStatus.COMPLETED,
                updatedAt = System.currentTimeMillis()
            )

            // Очищаем текущий челлендж у пользователя, если это был он
            val current = userCurrentChallengeDao.getCurrentUserChallenge(participant.userId)
            if (current?.challengeId == challengeId) {
                userCurrentChallengeDao.clearCurrentUserChallenge(participant.userId)
            }
        }

        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getUserActiveChallenges(userId: String): List<Challenge> {
        val participations = challengeParticipantDao.getUserParticipations(userId)
            .filter { it.status == ParticipantStatus.JOINED }
        val challengeIds = participations.map { it.challengeId }
        return challengeIds.mapNotNull { challengeId ->
            challengeDao.getChallengeById(challengeId)?.toChallengeDomain()
        }.filter { it.isActive }
    }

    override suspend fun getUserCompletedChallenges(userId: String): List<Challenge> {
        val participations = challengeParticipantDao.getUserParticipations(userId)
            .filter { it.status == ParticipantStatus.COMPLETED }
        val challengeIds = participations.map { it.challengeId }
        return challengeIds.mapNotNull { challengeId ->
            challengeDao.getChallengeById(challengeId)?.toChallengeDomain()
        }
    }

    //----поискк
    override suspend fun searchChallenges(query: String): List<Challenge> {
        return challengeDao.searchChallenges(query).toChallengeDomainList()
    }

    override suspend fun getPopularChallenges(limit: Int): List<Challenge> {
        return challengeDao.getPopularChallenges(limit).toChallengeDomainList()
    }
}