package com.example.hubtrackerapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HabitProgressDao {

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun saveProgress(habitProgress: HabitProgressDbModel)

    //в реализации базы создать доп функцию временную туда загонять habitId:String, date: LocalDate ,создать HabitProgressDbModel и передать сюда
    //просто вернуть значение готорое загнали сюда
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addProgressForHabit(progress: HabitProgressDbModel)
    @Query("SELECT * FROM habit_progress WHERE habitId = :habitId AND date = :date")
    suspend fun getProgress(habitId:String, date: LocalDate): HabitProgressDbModel?

    @Query("""
        UPDATE habit_progress 
        SET isCompleted = NOT isCompleted 
        WHERE habitId = :habitId AND date = :date
    """)
    suspend fun switchCompleteStatus( habitId: String, date: LocalDate)

    @Query("SELECT * FROM habit_progress WHERE date = :date")
    suspend fun getProgressForDate(date: String): List<HabitProgressDbModel>

    @Query("SELECT * FROM habit_progress WHERE date = :date")
    fun getProgressForDateFlow(date: String): Flow<List<HabitProgressDbModel>>

    //для очистки при выходе из профиля
    @Query("DELETE FROM habit_progress")
    suspend fun clearAllProgress()

    // потом для статистики получения в периоде прогресса между а и б
    @Query("""
        SELECT * FROM habit_progress 
        WHERE habitId = :habitId 
        AND date BETWEEN :startDate AND :endDate
    """)
    suspend fun getProgressForPeriod(
        habitId: String,
        startDate: String,
        endDate: String
    ): List<HabitProgressDbModel>
}