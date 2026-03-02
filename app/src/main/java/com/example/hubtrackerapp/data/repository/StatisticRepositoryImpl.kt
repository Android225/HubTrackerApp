package com.example.hubtrackerapp.data.repository

import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.dao.statistic.UserActionDao
import com.example.hubtrackerapp.data.firebase.FirebaseRepository
import com.example.hubtrackerapp.data.mapper.statistic.toUserActionDbModel
import com.example.hubtrackerapp.data.mapper.statistic.toUserActionEntity
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ChallengeMetadata
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.DetailedStats
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.MoodEntry
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ProfileStats
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.StatsPeriod
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import com.google.gson.Gson
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val firebaseRepository: FirebaseRepository,
    private val userActionDao: UserActionDao
): StatisticRepository {

    private val gson = Gson()

    /*ДОП ДЕЙСТВИЯ*/
    private suspend fun getCurrentUserId(): String {
        return userDao.getUserId() ?: throw IllegalStateException("User not logged in")
    }

    private fun LocalDate.toStartOfDayMillis(): Long =
        this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    private fun LocalDate.toEndOfDayMillis(): Long =
        this.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1

    private fun Long.toLocalDate(): LocalDate =
        Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

    //ОСНОВНОЕ
    override suspend fun logAction(
        actionType: ActionType,
        entityId: String?,
        entityName: String?,
        pointsEarned: Int,
        additionalData: String?
    ): Result<UserAction> = try {
        val userId = getCurrentUserId()
        val action = UserAction(
            userActionId = UUID.randomUUID().toString(),
            userId = userId,
            actionType = actionType,
            entityId = entityId,
            entityName = entityName,
            pointEarned = pointsEarned,
            timeStamp = System.currentTimeMillis(),
            additionalData = additionalData
        )
        userActionDao.insertAction(action.toUserActionDbModel())

        // TODO: ДОбавить потмо синхронизацию с базой Firebase
        //типо firebaseRepository.logAction(action)

        Result.success(action)
    }catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logAction(action: UserAction): Result<UserAction> = try {
        userActionDao.insertAction(action.toUserActionDbModel())
        // TODO: ДОбавить потмо синхронизацию с базой Firebase
        //типо firebaseRepository.logAction(action)
        Result.success(action)
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun logHabitCompleted(
        habitId: String,
        habitName: String,
        pointsEarned: Int,
        value: Int?
    ): Result<UserAction> {
        val additionalData = value?.let {
            gson.toJson(mapOf("value" to it))
        }
        return logAction(
            actionType = ActionType.HABIT_COMPLETED,
            entityId = habitId,
            entityName = habitName,
            pointsEarned = pointsEarned,
            additionalData = additionalData
        )
    }

    override suspend fun logHabitSkipped(
        habitId: String,
        habitName: String
    ): Result<UserAction> = logAction(
        actionType = ActionType.HABIT_SKIPPED,
        entityId = habitId,
        entityName = habitName,
        pointsEarned = 0
    )

    override suspend fun logHabitFailed(
        habitId: String,
        habitName: String
    ): Result<UserAction> = logAction(
        actionType = ActionType.HABIT_FAILED,
        entityId = habitId,
        entityName = habitName,
        pointsEarned = 0
    )

    override suspend fun logMood(
        mood: Int,
        emoji: String?,
        note: String?
    ): Result<UserAction>  {
        val moodData = mapOf(
            "mood" to mood,
            "emoji" to emoji,
            "note" to note
        )
        val additionalData = gson.toJson(moodData)

        return logAction(
            actionType = ActionType.MOOD_LOGGED,
            pointsEarned = 1,
            additionalData = additionalData
        )
    }

    override suspend fun logChallengeStarted(
        challengeId: String,
        challengeName: String,
        challengeData: ChallengeMetadata?
    ): Result<UserAction> {
        val additionalData = challengeData?.let { gson.toJson(it) }
        return logAction(
            actionType = ActionType.CHALLENGE_STARTED,
            entityId = challengeId,
            entityName = challengeName,
            pointsEarned = 5,
            additionalData = additionalData
        )
    }

    override suspend fun logChallengeCompleted(
        challengeId: String,
        challengeName: String,
        pointsEarned: Int
    ): Result<UserAction> = logAction(
        actionType = ActionType.CHALLENGE_COMPLETED,
        entityId = challengeId,
        entityName = challengeName,
        pointsEarned = pointsEarned
    )

    override suspend fun logClubJoined(
        clubId: String,
        clubName: String
    ): Result<UserAction> = logAction(
        actionType = ActionType.CLUB_JOINED,
        entityId = clubId,
        entityName = clubName,
        pointsEarned = 0
    )

    override suspend fun logClubLeave(
        clubId: String,
        clubName: String
    ): Result<UserAction> = logAction(
        actionType = ActionType.CLUB_LEFT,
        entityId = clubId,
        entityName = clubName,
        pointsEarned = 0
    )

    override suspend fun getAllUserActions(userId: String): List<UserAction> {
        return userActionDao.getAllUserActions(userId).map { it.toUserActionEntity() }
    }

    override suspend fun getUserActionsInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserAction> {
        return userActionDao.getUserActionsInRange(userId, fromTimestamp, toTimestamp)
            .map { it.toUserActionEntity() }
    }

    override suspend fun getUserActionsForDate(
        userId: String,
        date: LocalDate
    ): List<UserAction> {
        val startOfDay = date.toStartOfDayMillis()
        val endOfDay = date.toEndOfDayMillis()
        return userActionDao.getUserActionsInRange(userId, startOfDay, endOfDay)
            .map { it.toUserActionEntity() }
    }

    override suspend fun getUserActionsByType(
        userId: String,
        actionType: ActionType
    ): List<UserAction> {
        return userActionDao.getUserActionsByType(userId, actionType)
            .map { it.toUserActionEntity() }
    }

    override suspend fun getUserActionsByTypeInRange(
        userId: String,
        actionType: ActionType,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserAction> {
        return userActionDao.getUserActionsByTypeInRange(userId, actionType, fromTimestamp, toTimestamp)
            .map { it.toUserActionEntity() }
    }

    override suspend fun getHabitsCompletedCount(
        userId: String,
        date: LocalDate
    ): Int {
        val startOfDay = date.toStartOfDayMillis()
        val endOfDay = date.toEndOfDayMillis()
        return userActionDao.getActionCountInRange(userId, ActionType.HABIT_COMPLETED, startOfDay, endOfDay)
    }

    override suspend fun getHabitsCompletedCountInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Int {
        return userActionDao.getActionCountInRange(userId, ActionType.HABIT_COMPLETED, fromTimestamp, toTimestamp)
    }

    override suspend fun getHabitsSkippedCount(
        userId: String,
        date: LocalDate
    ): Int {
        val startOfDay = date.toStartOfDayMillis()
        val endOfDay = date.toEndOfDayMillis()
        return userActionDao.getActionCountInRange(userId, ActionType.HABIT_SKIPPED, startOfDay, endOfDay)
    }

    override suspend fun getHabitsFailedCount(
        userId: String,
        date: LocalDate
    ): Int {
        val startOfDay = date.toStartOfDayMillis()
        val endOfDay = date.toEndOfDayMillis()
        return userActionDao.getActionCountInRange(userId, ActionType.HABIT_FAILED, startOfDay, endOfDay)
    }

    override suspend fun getPointsEarnedForDate(
        userId: String,
        date: LocalDate
    ): Int {
        val startOfDay = date.toStartOfDayMillis()
        val endOfDay = date.toEndOfDayMillis()
        return userActionDao.getPointsEarnedInRange(userId, startOfDay, endOfDay) ?: 0
    }

    override suspend fun getPointsEarnedInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Int {
        return userActionDao.getPointsEarnedInRange(userId, fromTimestamp, toTimestamp) ?: 0
    }

    override suspend fun getTotalPoints(userId: String): Int {
        return userActionDao.getTotalPoints(userId) ?: 0
    }

    override suspend fun getDailyCompletions(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int> {
        val fromTimestamp = fromDate.toStartOfDayMillis()
        val toTimestamp = toDate.toEndOfDayMillis()

        val actions = userActionDao.getUserActionsByTypeInRange(
            userId = userId,
            actionType = ActionType.HABIT_COMPLETED,
            fromTimestamp = fromTimestamp,
            toTimestamp = toTimestamp
        )

        return actions.groupBy { it.timestamp.toLocalDate() }
            .mapValues { it.value.size }
    }

    override suspend fun getDailyPoints(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int> {
        val fromTimestamp = fromDate.toStartOfDayMillis()
        val toTimestamp = toDate.toEndOfDayMillis()

        val actions = userActionDao.getUserActionsInRange(userId, fromTimestamp, toTimestamp)

        return actions.groupBy { it.timestamp.toLocalDate() }
            .mapValues { entry -> entry.value.sumOf { it.pointsEarned } }
    }

    override suspend fun getCurrentStreak(userId: String): Int {
        var streak = 0
        var currentDate = LocalDate.now()

        while (hasProgressOnDate(userId, currentDate)) {
            streak++
            currentDate = currentDate.minusDays(1)
        }

        return streak
    }

    override suspend fun getLongestStreak(userId: String): Int {
        val history = getStreakHistory(userId, 365) // последний год
        var currentStreak = 0
        var maxStreak = 0

        for ((_, hasProgress) in history) {
            if (hasProgress) {
                currentStreak++
                maxStreak = maxOf(maxStreak, currentStreak)
            } else {
                currentStreak = 0
            }
        }

        return maxStreak
    }

    override suspend fun hasProgressToday(userId: String): Boolean {
        return hasProgressOnDate(userId, LocalDate.now())
    }

    override suspend fun hasProgressOnDate(
        userId: String,
        date: LocalDate
    ): Boolean {
        val startOfDay = date.toStartOfDayMillis()
        val endOfDay = date.toEndOfDayMillis()
        return userActionDao.hasProgressOnDate(userId, startOfDay, endOfDay)
    }

    override suspend fun getStreakHistory(
        userId: String,
        days: Int
    ): List<Pair<LocalDate, Boolean>> {
        val endDate = LocalDate.now()
        val startDate = endDate.minusDays(days.toLong() - 1)

        return (0 until days).map { offset ->
            val date = startDate.plusDays(offset.toLong())
            date to hasProgressOnDate(userId, date)
        }
    }

    override suspend fun getMoodEntries(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): List<MoodEntry> {
        val fromTimestamp = fromDate.toStartOfDayMillis()
        val toTimestamp = toDate.toEndOfDayMillis()

        val moodActions = userActionDao.getUserActionsByTypeInRange(
            userId = userId,
            actionType = ActionType.MOOD_LOGGED,
            fromTimestamp = fromTimestamp,
            toTimestamp = toTimestamp
        )

        return moodActions.mapNotNull { action ->
            action.additionalData?.let { json ->
                try {
                    val map = gson.fromJson(json, Map::class.java)
                    MoodEntry(
                        date = action.timestamp.toLocalDate(),
                        mood = (map["mood"] as Double).toInt(),
                        emoji = map["emoji"] as? String,
                        note = map["note"] as? String
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    override suspend fun getAverageMood(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Float? {
        val entries = getMoodEntries(userId, fromDate, toDate)
        return if (entries.isNotEmpty()) {
            entries.map { it.mood }.average().toFloat()
        } else {
            null
        }
    }

    override suspend fun getDailyMood(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int> {
        val entries = getMoodEntries(userId, fromDate, toDate)
        return entries.associate { it.date to it.mood }
    }

    override suspend fun getProfileStats(userId: String): ProfileStats {
        val today = LocalDate.now()
        val weekAgo = today.minusDays(7)
        val monthAgo = today.minusMonths(1)

        val weekStart = weekAgo.toStartOfDayMillis()
        val monthStart = monthAgo.toStartOfDayMillis()
        val todayStart = today.toStartOfDayMillis()
        val todayEnd = today.toEndOfDayMillis()
        val now = System.currentTimeMillis()

        return ProfileStats(
            totalHabitsCompleted = userActionDao.getTotalActionCount(userId, ActionType.HABIT_COMPLETED),
            totalPoints = userActionDao.getTotalPoints(userId) ?: 0,
            currentStreak = getCurrentStreak(userId),
            longestStreak = getLongestStreak(userId),
            averageMood = getAverageMood(userId, monthAgo, today),
            achievementsCount = 0, // TODO: добавить когда будут ачивки
            todayCompletions = userActionDao.getActionCountInRange(userId, ActionType.HABIT_COMPLETED, todayStart, todayEnd),
            weeklyCompletions = userActionDao.getActionCountInRange(userId, ActionType.HABIT_COMPLETED, weekStart, now),
            monthlyCompletions = userActionDao.getActionCountInRange(userId, ActionType.HABIT_COMPLETED, monthStart, now),
            lastActiveDate = userActionDao.getUserActionsInRange(userId, monthStart, now)
                .maxByOrNull { it.timestamp }?.timestamp?.toLocalDate()
        )
    }

    override suspend fun getDetailedStats(
        userId: String,
        period: StatsPeriod
    ): DetailedStats {
        val now = System.currentTimeMillis()
        val (fromDate, toDate) = when (period) {
            StatsPeriod.DAY -> {
                val today = LocalDate.now()
                today to today
            }
            StatsPeriod.WEEK -> {
                val end = LocalDate.now()
                val start = end.minusDays(6)
                start to end
            }
            StatsPeriod.MONTH -> {
                val end = LocalDate.now()
                val start = end.minusDays(29)
                start to end
            }
            StatsPeriod.ALL_TIME -> {
                val start = LocalDate.of(2020, 1, 1)
                val end = LocalDate.now()
                start to end
            }
        }

        val fromTimestamp = fromDate.toStartOfDayMillis()
        val toTimestamp = toDate.toEndOfDayMillis()

        val completions = getDailyCompletions(userId, fromDate, toDate)
        val bestDay = completions.maxByOrNull { it.value }

        return DetailedStats(
            period = period,
            totalCompletions = completions.values.sum(),
            totalPoints = getPointsEarnedInRange(userId, fromTimestamp, toTimestamp),
            averagePerDay = if (completions.isNotEmpty()) completions.values.sum().toFloat() / completions.size else 0f,
            bestDay = bestDay?.key,
            bestDayValue = bestDay?.value ?: 0,
            dailyBreakdown = completions,
            moodAverage = getAverageMood(userId, fromDate, toDate),
            moodBreakdown = getDailyMood(userId, fromDate, toDate)
        )
    }
}