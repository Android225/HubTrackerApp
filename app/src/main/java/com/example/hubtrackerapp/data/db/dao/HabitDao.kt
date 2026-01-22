package com.example.hubtrackerapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.HabitDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    // Получить все привычки текущего пользователя
    @Query("SELECT * FROM habits WHERE userId = :userId")
    suspend fun getAllHabitsByUserId(userId: String): List<HabitDbModel>

    // Получить все активные привычки текущего пользователя
    @Query("SELECT * FROM habits WHERE userId = :userId AND isArchived = 0")
    suspend fun getAllActiveHabitsByUserId(userId: String): List<HabitDbModel>

    // Получить ТОЛЬКО архивные привычки
    @Query("SELECT * FROM habits WHERE userId = :userId AND isArchived = 1")
    suspend fun getArchivedHabitsByUserId(userId: String): List<HabitDbModel>

    // Получить Flow активных привычек (для Compose) +++++
    @Query("SELECT * FROM habits WHERE userId = :userId AND isArchived = 0")
    fun getActiveHabitsFlow(userId: String): Flow<List<HabitDbModel>>

    //добавление нового + редактирование  +++++
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addHabit(habit: HabitDbModel)

    //добавляются в архив(перестаю быть видимыми isArchived = 1) +++++
    @Query("UPDATE habits SET isArchived = 1 WHERE habitId = :habitId")
    suspend fun deleteHabit(habitId: String)

//    // Восстановление из архива isArchived = 0
//    @Query("UPDATE habits SET isArchived = 0 WHERE habitId = :habitId")
//    suspend fun restoreHabit(habitId: String)


    @Query("DELETE FROM habits")
    suspend fun clearHabits()

}