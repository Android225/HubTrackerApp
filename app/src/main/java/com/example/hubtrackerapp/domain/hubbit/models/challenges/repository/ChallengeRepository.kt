package com.example.hubtrackerapp.domain.hubbit.models.challenges.repository

import HabitMetric
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeGoalType
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeHabit
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeInvitation
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.UserCurrentChallenge
import java.time.LocalDate

interface ChallengeRepository {

    // -- main operations

    suspend fun createChallenge(
        title: String,
        description: String,
        visibility: ChallengeVisibility,
        clubId: String? = null,
        goalType: ChallengeGoalType,
        targetMetric: HabitMetric? = null,
        targetValue: Int? = null,
        startDate: LocalDate,
        endDate: LocalDate? = null,
        maxParticipants: Int? = null
    ): Result<Challenge>

    suspend fun getChallengeById(challengeId: String): Challenge?

    //получение с фильтрацией
    suspend fun getChallenges(
        visibility: ChallengeVisibility? = null,
        clubId: String? = null,
        isActive: Boolean? = null
    ): List<Challenge>

    suspend fun getGlobalChallenges(): List<Challenge>

    //все челенджи клуба
    suspend fun getClubChallenges(clubId: String): List<Challenge>

    suspend fun updateChallenge(challenge: Challenge): Result<Challenge>

    suspend fun deleteChallenge(challengeId: String): Result<Unit>

    //----добавление хобби в челендж
    suspend fun addChallengeHabit(
        challengeId: String,
        habitId: String,
        habitName: String,
        habitIcon: String,
        habitColor: Int,
        metric: HabitMetric,
        target: String,
        scheduleType: String,
        scheduleValue: String?
    ): Result<ChallengeHabit>

    suspend fun getChallengeHabits(challengeId: String): List<ChallengeHabit>

    suspend fun removeChallengeHabit(challengeId: String, habitId: String): Result<Unit>

    //------участники

    suspend fun joinChallenge(challengeId: String): Result<ChallengeParticipant>

    suspend fun leaveChallenge(challengeId: String): Result<Unit>

    suspend fun getChallengeParticipants(challengeId: String): List<ChallengeParticipant>

    //прогресс конкретного user
    suspend fun getParticipantProgress(
        challengeId: String,
        userId: String
    ): ChallengeParticipant?

    //при выполнении хобби челенджа изменение прогресса
    suspend fun updateParticipantProgress(
        challengeId: String,
        userId: String,
        value: Int
    ): Result<ChallengeParticipant>

    //проверка на участие в челендже
    suspend fun isUserParticipating(challengeId: String, userId: String): Boolean

    //---- Приглашения в челендж

    suspend fun sendInvitation(
        challengeId: String,
        invitedUserId: String,
        message: String? = null
    ): Result<ChallengeInvitation>

    suspend fun acceptInvitation(invitationId: String): Result<ChallengeParticipant>
    suspend fun declineInvitation(invitationId: String): Result<Unit>
    suspend fun getIncomingInvitations(userId: String): List<ChallengeInvitation>
    suspend fun getOutgoingInvitations(userId: String): List<ChallengeInvitation>

    //------ текущий челендж пользователя
    suspend fun getCurrentUserChallenge(userId: String): UserCurrentChallenge?

    //установить текущий челендж (всего 1 (пока-что??))
    suspend fun setCurrentUserChallenge(
        userId: String,
        challenge: Challenge,
        participant: ChallengeParticipant
    ): Result<Unit>

    //при выходе/завершении текущий челендж удаляется
    suspend fun clearCurrentUserChallenge(userId: String): Result<Unit>

    //проверка есть ли челендж активный
    suspend fun hasActiveChallenge(userId: String): Boolean

    //---- прогресс со статистикой

    suspend fun getChallengeTotalProgress(challengeId: String): Int

    //рейтинг учатсинков
    suspend fun getParticipantRanking(challengeId: String): List<ChallengeParticipant>
    suspend fun completeChallenge(challengeId: String): Result<Unit>
    suspend fun getUserActiveChallenges(userId: String): List<Challenge>
    suspend fun getUserCompletedChallenges(userId: String): List<Challenge>


    //--- поискк
    suspend fun searchChallenges(query: String): List<Challenge>
    //популярные челенджи (сортировка по кол-ву участников)
    suspend fun getPopularChallenges(limit: Int = 10): List<Challenge>
}