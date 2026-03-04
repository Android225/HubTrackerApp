package com.example.hubtrackerapp.data.repository

import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.dao.club.ClubDao
import com.example.hubtrackerapp.data.db.dao.club.ClubFeedDao
import com.example.hubtrackerapp.data.db.dao.club.ClubStatsDao
import com.example.hubtrackerapp.data.db.dao.club.UserInClubDao
import com.example.hubtrackerapp.data.db.model.club.ClubDBModel
import com.example.hubtrackerapp.data.db.model.club.ClubFeedDBModel
import com.example.hubtrackerapp.data.db.model.club.ClubStatsDBModel
import com.example.hubtrackerapp.data.db.model.club.UserInClubDBModel
import com.example.hubtrackerapp.data.mapper.club.toClubDbModel
import com.example.hubtrackerapp.data.mapper.club.toClubDomain
import com.example.hubtrackerapp.data.mapper.club.toClubDomainList
import com.example.hubtrackerapp.data.mapper.club.toClubFeedDomain
import com.example.hubtrackerapp.data.mapper.club.toClubFeedDomainList
import com.example.hubtrackerapp.data.mapper.club.toClubStatsDomain
import com.example.hubtrackerapp.data.mapper.club.toUserInClubDomain
import com.example.hubtrackerapp.data.mapper.club.toUserInClubDomainList
import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubFeed
import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubStats
import com.example.hubtrackerapp.domain.hubbit.models.club.model.RoleMode
import com.example.hubtrackerapp.domain.hubbit.models.club.model.UserInClub
import com.example.hubtrackerapp.domain.hubbit.models.club.repository.ClubRepository
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import java.util.UUID
import javax.inject.Inject

class ClubRepositoryImpl @Inject constructor(
    private val clubDao: ClubDao,
    private val userInClubDao: UserInClubDao,
    private val clubStatsDao: ClubStatsDao,
    private val clubFeedDao: ClubFeedDao,
    private val userDao: UserDao
) : ClubRepository {

    private suspend fun getCurrentUserId(): String {
        return userDao.getUserId() ?: throw IllegalStateException("User not logged in")
    }

    override suspend fun createClub(
        title: String,
        description: String,
        imageUrl: String,
        category: String,
        isPrivate: Boolean
    ): Result<Club> = try {
        val currentUserId = getCurrentUserId()
        val now = System.currentTimeMillis()

        val club = ClubDBModel(
            clubId = UUID.randomUUID().toString(),
            adminId = currentUserId,
            title = title,
            description = description,
            imageUrl = imageUrl,
            category = category,
            createdAt = now,
            isPrivate = isPrivate,
            lastUpdate = now,
            memberCount = 1 // админ сразу участник
        )

        clubDao.insertClub(club)

        // Добавляем админа как участника
        val userInClub = UserInClubDBModel(
            userId = currentUserId,
            clubId = club.clubId,
            role = RoleMode.ADMIN,
            joinedAt = now
        )
        userInClubDao.insertUserInClub(userInClub)

        // Создаем статистику
        val stats = ClubStatsDBModel(
            clubId = club.clubId,
            lastUpdated = now
        )
        clubStatsDao.insertClubStats(stats)

        // Добавляем событие в ленту
        addFeedEvent(
            clubId = club.clubId,
            userId = currentUserId,
            userName = userDao.getCurrentUser()?.firstName ?: "Пользователь",
            userImageUrl = null,
            actionType = ActionType.CLUB_JOINED,
            message = "создал клуб"
        )

        Result.success(club.toClubDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getClubById(clubId: String): Club? {
        return clubDao.getClubById(clubId)?.toClubDomain()
    }

    override suspend fun getClubs(
        category: String?,
        isPrivate: Boolean?
    ): List<Club> {
        return clubDao.getClubs(category, isPrivate).toClubDomainList()
    }

    override suspend fun updateClub(club: Club): Result<Club> {
        return try {
            val existing = clubDao.getClubById(club.clubId)
                ?: return Result.failure(Exception("Club not found"))

            // Проверяем права
            val currentUserId = getCurrentUserId()
            if (existing.adminId != currentUserId) {
                return Result.failure(Exception("Only admin can update club"))
            }

            val updatedClub = club.copy(lastUpdate = System.currentTimeMillis())
            clubDao.updateClub(updatedClub.toClubDbModel())

            Result.success(updatedClub)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteClub(clubId: String): Result<Unit> {
        return try {
            val club = clubDao.getClubById(clubId)
                ?: return Result.failure(Exception("Club not found"))

            val currentUserId = getCurrentUserId()
            if (club.adminId != currentUserId) {
                return Result.failure(Exception("Only admin can delete club"))
            }

            clubDao.deleteClub(club)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //---- участники клубов
    override suspend fun joinClub(clubId: String): Result<UserInClub> {
        return try {
            val currentUserId = getCurrentUserId()
            val now = System.currentTimeMillis()

            // Проверяем, не состоит ли уже
            if (userInClubDao.isUserInClub(currentUserId, clubId)) {
                return Result.failure(Exception("Already in club"))
            }

            val club = clubDao.getClubById(clubId)
                ?: return Result.failure(Exception("Club not found"))

            // Если клуб приватный - нужна проверка приглашения
            if (club.isPrivate) {
                return Result.failure(Exception("Private club requires invitation"))
            }

            val userInClub = UserInClubDBModel(
                userId = currentUserId,
                clubId = clubId,
                role = RoleMode.MEMBER,
                joinedAt = now
            )
            userInClubDao.insertUserInClub(userInClub)

            // Обновляем счетчик участников
            clubDao.updateMemberCount(clubId, 1)

            // Добавляем событие в ленту
            addFeedEvent(
                clubId = clubId,
                userId = currentUserId,
                userName = userDao.getCurrentUser()?.firstName ?: "Пользователь",
                userImageUrl = null,
                actionType = ActionType.CLUB_JOINED,
                message = "присоединился к клубу"
            )

            Result.success(userInClub.toUserInClubDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun leaveClub(clubId: String): Result<Unit> {
        return try {
            val currentUserId = getCurrentUserId()

            val userInClub = userInClubDao.getUserClubs(currentUserId)
                .find { it.clubId == clubId }
                ?: return Result.failure(Exception("Not a member of this club"))

            // Админ не может выйти, только передать права или удалить клуб
            if (userInClub.role == RoleMode.ADMIN) {
                return Result.failure(Exception("Admin cannot leave. Transfer admin role or delete club."))
            }

            userInClubDao.removeMember(clubId, currentUserId)
            clubDao.updateMemberCount(clubId, -1)

            // Добавляем событие в ленту
            addFeedEvent(
                clubId = clubId,
                userId = currentUserId,
                userName = userDao.getCurrentUser()?.firstName ?: "Пользователь",
                userImageUrl = null,
                actionType = ActionType.CLUB_LEFT,
                message = "покинул клуб"
            )

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getClubMembers(clubId: String): List<UserInClub> {
        return userInClubDao.getClubMembers(clubId).toUserInClubDomainList()
    }

    override suspend fun isUserInClub(
        userId: String,
        clubId: String
    ): Boolean {
        return userInClubDao.isUserInClub(userId, clubId)
    }

    override suspend fun getUserClubs(userId: String): List<Club> {
        val userClubIds = userInClubDao.getUserClubs(userId).map { it.clubId }
        return userClubIds.mapNotNull { clubId ->
            clubDao.getClubById(clubId)?.toClubDomain()
        }
    }

    override suspend fun updateMemberRole(
        clubId: String,
        userId: String,
        newRole: RoleMode
    ): Result<UserInClub> {
        return try {
            val currentUserId = getCurrentUserId()

            // Проверяем, что текущий пользователь - админ
            val currentUserRole = userInClubDao.getUserRole(clubId, currentUserId)
            if (currentUserRole != RoleMode.ADMIN) {
                return Result.failure(Exception("Only admin can change roles"))
            }

            userInClubDao.updateMemberRole(clubId, userId, newRole)

            val updated = userInClubDao.getUserClubs(userId)
                .find { it.clubId == clubId }
                ?: return Result.failure(Exception("User not found"))

            Result.success(updated.toUserInClubDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun removeMember(
        clubId: String,
        userId: String
    ): Result<Unit> {
        return try {
            val currentUserId = getCurrentUserId()

            // Проверяем права
            val currentUserRole = userInClubDao.getUserRole(clubId, currentUserId)
            if (currentUserRole != RoleMode.ADMIN && currentUserRole != RoleMode.MODERATOR) {
                return Result.failure(Exception("Not enough rights"))
            }

            userInClubDao.removeMember(clubId, userId)
            clubDao.updateMemberCount(clubId, -1)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    //-----статистика

    override suspend fun getClubStats(clubId: String): ClubStats? {
        return clubStatsDao.getClubStats(clubId)?.toClubStatsDomain()
    }

    override suspend fun updateClubStats(
        clubId: String,
        completionsToAdd: Int,
        challengesToAdd: Int
    ): Result<ClubStats> = try {
        clubStatsDao.updateClubStats(
            clubId = clubId,
            completionsToAdd = completionsToAdd,
            challengesToAdd = challengesToAdd,
            lastUpdated = System.currentTimeMillis()
        )

        val updated = clubStatsDao.getClubStats(clubId)
            ?: return Result.failure(Exception("Stats not found"))

        Result.success(updated.toClubStatsDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun setCurrentChallenge(
        clubId: String,
        challengeId: String?
    ): Result<Unit> = try {
        clubStatsDao.setCurrentChallenge(clubId, challengeId)
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    //--лента событий клуба

    override suspend fun addFeedEvent(
        clubId: String,
        userId: String,
        userName: String,
        userImageUrl: String?,
        actionType: ActionType,
        message: String,
        entityId: String?,
        entityName: String?
    ): Result<ClubFeed> = try {
        val event = ClubFeedDBModel(
            clubFeedId = UUID.randomUUID().toString(),
            clubId = clubId,
            userId = userId,
            userName = userName,
            userImageUrl = userImageUrl,
            actionType = actionType,
            message = message,
            entityId = entityId,
            entityName = entityName,
            timestamp = System.currentTimeMillis()
        )

        clubFeedDao.insertFeedEvent(event)

        // Оставляем только последние 100 записей
        clubFeedDao.deleteOldFeedEvents(clubId, System.currentTimeMillis() - 30_000_000) // примерно месяц

        Result.success(event.toClubFeedDomain())
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun getClubFeed(
        clubId: String,
        limit: Int,
        offset: Long
    ): List<ClubFeed> {
        return clubFeedDao.getClubFeed(clubId, limit, offset).toClubFeedDomainList()
    }

    //-----поиск


    override suspend fun searchClubs(query: String): List<Club> {
        return clubDao.searchClubs(query).toClubDomainList()
    }

    override suspend fun getPopularClubs(limit: Int): List<Club> {
        return clubDao.getPopularClubs(limit).toClubDomainList()
    }

    override suspend fun getMembersCount(clubId: String): Int {
        return clubDao.getMembersCount(clubId)
    }

    //доп проверка мб потом удалить
    override suspend fun isAdmin(userId: String, clubId: String): Boolean {
        return userInClubDao.getUserRole(clubId, userId) == RoleMode.ADMIN
    }
}