package com.example.hubtrackerapp.domain.hubbit.models.club.repository

import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubFeed
import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubStats
import com.example.hubtrackerapp.domain.hubbit.models.club.model.RoleMode
import com.example.hubtrackerapp.domain.hubbit.models.club.model.UserInClub
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType

interface ClubRepository {

    //создать клуб
    suspend fun createClub(
        title: String,
        description: String,
        imageUrl: String,
        category: String,
        isPrivate: Boolean
    ): Result<Club>

    //взять по айди
    suspend fun getClubById(clubId: String): Club?

    //список всех клубов с фильтрацией
    suspend fun getClubs(
        category: String? = null,
        isPrivate: Boolean? = null
    ): List<Club>

    //обновить инфу клуба
    suspend fun updateClub(club: Club): Result<Club>
    suspend fun deleteClub(clubId: String): Result<Unit>

    // участники
    suspend fun joinClub(clubId: String): Result<UserInClub>

    suspend fun leaveClub(clubId: String): Result<Unit>

    suspend fun getClubMembers(clubId: String): List<UserInClub>

    suspend fun isUserInClub(userId: String, clubId: String): Boolean

    suspend fun getUserClubs(userId: String): List<Club>

    suspend fun updateMemberRole(
        clubId: String,
        userId: String,
        newRole: RoleMode
    ): Result<UserInClub>

    suspend fun removeMember(clubId: String, userId: String): Result<Unit>

    //статистика клуба
    suspend fun getClubStats(clubId: String): ClubStats?

    //обновить статистику клуба (скорей всего вызов при выполнении действий)
    suspend fun updateClubStats(
        clubId: String,
        completionsToAdd: Int = 1,
        challengesToAdd: Int = 0
    ): Result<ClubStats>

    //установить челендж для клуба
    suspend fun setCurrentChallenge(clubId: String, challengeId: String?): Result<Unit>

    //лента с событиями пользователей


    //добавить событие
    suspend fun addFeedEvent(
        clubId: String,
        userId: String,
        userName: String,
        userImageUrl: String?,
        actionType: ActionType,
        message: String,
        entityId: String? = null,
        entityName: String? = null
    ): Result<ClubFeed>

    suspend fun getClubFeed(
        clubId: String,
        limit: Int = 50,
        offset: Long = 0
    ): List<ClubFeed>


    //поиск/фильтрация

    //поиск по названию или description
    suspend fun searchClubs(query: String): List<Club>

    //популярные клубы
    suspend fun getPopularClubs(limit: Int = 10): List<Club>

    //-----действия с клубом

    suspend fun getMembersCount(clubId: String): Int
    //проверка на админа
    suspend fun isAdmin(userId: String, clubId: String): Boolean


}
