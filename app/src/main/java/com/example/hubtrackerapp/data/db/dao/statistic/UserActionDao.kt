package com.example.hubtrackerapp.data.db.dao.statistic

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.statistic.UserActionDBModel
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType

@Dao
interface UserActionDao {

    //*
    @Insert
    suspend fun insertAction(action: UserActionDBModel)


    //Все действия пользователя *
    @Query("SELECT * FROM user_action WHERE userId = :userId ORDER BY timestamp DESC")
    suspend fun getAllUserActions(userId: String): List<UserActionDBModel>

    ///Все действия пользователя за период времени *
    @Query("SELECT * FROM user_action WHERE userId = :userId AND timestamp BETWEEN :fromTimestamp AND :toTimestamp ORDER BY timestamp DESC")
    suspend fun getUserActionsInRange(
        userId: String,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserActionDBModel>

    //получение по типу *
    @Query("SELECT * FROM user_action WHERE userId = :userId AND actionType = :actionType ORDER BY timestamp DESC")
    suspend fun getUserActionsByType(
        userId: String,
        actionType: ActionType
    ): List<UserActionDBModel>

    //получение по типу за период времени *
    @Query("""
        SELECT * FROM user_action 
        WHERE userId = :userId 
        AND actionType = :actionType 
        AND timestamp BETWEEN :fromTimestamp AND :toTimestamp 
        ORDER BY timestamp DESC
    """)
    suspend fun getUserActionsByTypeInRange(
        userId: String,
        actionType: ActionType,
        fromTimestamp: Long,
        toTimestamp: Long
    ): List<UserActionDBModel>


    // Универсальный подсчет количества записей по actionType по диапазону *
    @Query("SELECT COUNT(*) FROM user_action WHERE userId = :userId AND actionType = :actionType AND timestamp BETWEEN :from AND :to")
    suspend fun getActionCountInRange(
        userId: String,
        actionType: ActionType,
        from: Long,
        to: Long
    ): Int
    // Универсальный подсчет всех записей по actionType
    @Query("SELECT COUNT(*) FROM user_action WHERE userId = :userId AND actionType = :actionType")
    suspend fun getTotalActionCount(
        userId: String,
        actionType: ActionType
    ): Int

    //подсчет очков за период */
    @Query("SELECT SUM(pointsEarned) FROM user_action WHERE userId = :userId AND timestamp BETWEEN :startOfDay AND :endOfDay")
    suspend fun getPointsEarnedInRange(
        userId: String,
        startOfDay: Long,
        endOfDay: Long
    ): Int?

    //подсчет всех очков *
    @Query("SELECT SUM(pointsEarned) FROM user_action WHERE userId = :userId")
    suspend fun getTotalPoints(userId: String): Int?

    //--для стрика --
    @Query("SELECT EXISTS(SELECT 1 FROM user_action WHERE userId = :userId AND actionType = 'HABIT_COMPLETED' AND timestamp BETWEEN :startOfDay AND :endOfDay LIMIT 1)")
    suspend fun hasProgressOnDate(
        userId: String,
        startOfDay: Long,
        endOfDay: Long
    ): Boolean

    @Query("DELETE FROM user_action WHERE userId = :userId")
    suspend fun deleteAllUserActions(userId: String)

    @Query("DELETE FROM user_action WHERE userActionId = :actionId")
    suspend fun deleteActionById(actionId: String)

    @Query("DELETE FROM user_action WHERE timestamp < :beforeTimestamp")
    suspend fun deleteActionsOlderThan(beforeTimestamp: Long)
    //МБ ПАГИНАЦИЮ ПОТОМ ЕЩЕ ДОБАВИТЬ
}