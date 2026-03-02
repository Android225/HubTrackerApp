package com.example.hubtrackerapp.domain.hubbit.models.statistic.repository

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ChallengeMetadata
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.DetailedStats
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.MoodEntry
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ProfileStats
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.StatsPeriod
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import java.time.LocalDate

interface StatisticRepository {

    //запись действий пользователя
    suspend fun logAction(
        actionType: ActionType,
        entityId: String? = null,
        entityName: String? = null,
        pointsEarned: Int = 0,
        additionalData: String? = null
    ): Result<UserAction>

    //запись готового объекта действия пользователя
    suspend fun logAction(action: UserAction): Result<UserAction>

    /*запись выполнения habit*/
    suspend fun logHabitCompleted(
        habitId: String,
        habitName: String,
        pointsEarned: Int,
        value: Int? = null  // для метрик (км, страницы)
    ): Result<UserAction>

    suspend fun logHabitSkipped(
        habitId: String,
        habitName: String
    ): Result<UserAction>

    suspend fun logHabitFailed(
        habitId: String,
        habitName: String
    ): Result<UserAction>

    /*запись выбранного настроения*/

    suspend fun logMood(
        mood: Int,           // 1-5
        emoji: String? = null,
        note: String? = null
    ): Result<UserAction>

    /*запись челенджей*/
    suspend fun logChallengeStarted(
        challengeId: String,
        challengeName: String,
        challengeData: ChallengeMetadata? = null
    ): Result<UserAction>

    suspend fun logChallengeCompleted(
        challengeId: String,
        challengeName: String,
        pointsEarned: Int
    ): Result<UserAction>

    /*запись для клубов*/

    suspend fun logClubJoined(
        clubId: String,
        clubName: String
    ): Result<UserAction>

    suspend fun logClubLeave(
        clubId: String,
        clubName: String
    ): Result<UserAction>

    /*достижения (РЕАЛИЗОВАТЬ НА ПРОДАКШЕНЕ)*/

    // -- Получение действий--

    suspend fun getAllUserActions(userId: String): List<UserAction>

    //за конкретный период
    suspend fun getUserActionsInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserAction>

    //конкретная дата
    suspend fun getUserActionsForDate(
        userId: String,
        date: LocalDate
    ): List<UserAction>

    //получение по определенному типу
    suspend fun getUserActionsByType(
        userId: String,
        actionType: ActionType
    ): List<UserAction>

    //определенный тип за период
    suspend fun getUserActionsByTypeInRange(
        userId: String,
        actionType: ActionType,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserAction>

    //--Вычисляемые--

    //количество выолненных привычек за день
    suspend fun getHabitsCompletedCount(
        userId: String,
        date: LocalDate
    ): Int

    //за период
    suspend fun getHabitsCompletedCountInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Int

    // пропущеные привычки за день
    suspend fun getHabitsSkippedCount(
        userId: String,
        date: LocalDate
    ): Int

    // количество полученных очков за день
    suspend fun getPointsEarnedForDate(
        userId: String,
        date: LocalDate
    ): Int

    // количество очков за период
    suspend fun getPointsEarnedInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): Int

    //все очки пользователя
    suspend fun getTotalPoints(userId: String): Int

    //статистика по дням для графиков статистики
    suspend fun getDailyCompletions(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int>

    //очки по дням для статистики
    suspend fun getDailyPoints(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int>


    // --стрики--
    // текущий стрик (дни подряд с выполненными привычками)
    suspend fun getCurrentStreak(userId: String): Int

    //лучший стрик
    suspend fun getLongestStreak(userId: String): Int

    // проверка прогресса на сегодня был или нет
    suspend fun hasProgressToday(userId: String): Boolean
    // проверка был ли прогресс в текущую дату
    suspend fun hasProgressOnDate(userId: String, date: LocalDate): Boolean


    // история стриков
    suspend fun getStreakHistory(
        userId: String,
        days: Int = 30
    ): List<Pair<LocalDate, Boolean>>


    /* -- настроение -- */

    //записи настроения за период
    suspend fun getMoodEntries(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): List<MoodEntry>

    //среднее настроение за период

    suspend fun getAverageMood(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Float?

    //настроение по дням для графиков
    suspend fun getDailyMood(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int>

    /*НА ПРОДАКШЕНЕ ДОБАВИТЬ ДОСТИЖЕНИЯ*/

    /*получение статистики для главного экрана*/

    //статистика для главного экрана
    suspend fun getProfileStats(userId: String): ProfileStats

    // статистика для экрана статистики
    suspend fun getDetailedStats(
        userId: String,
        period: StatsPeriod
    ): DetailedStats
}
