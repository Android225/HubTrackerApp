package com.example.hubtrackerapp.data.db.dao.challenge

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.challenge.ChallengeHabitDBModel

@Dao
interface ChallengeHabitDao {

    @Insert
    suspend fun insertChallengeHabit(habit: ChallengeHabitDBModel)

    @Insert
    suspend fun insertAllChallengeHabits(habits: List<ChallengeHabitDBModel>)

    @Query("SELECT * FROM challenge_habits WHERE challengeId = :challengeId")
    suspend fun getChallengeHabits(challengeId: String): List<ChallengeHabitDBModel>

    @Query("SELECT * FROM challenge_habits WHERE challengeId = :challengeId AND habitId = :habitId")
    suspend fun getChallengeHabit(challengeId: String, habitId: String): ChallengeHabitDBModel?

    @Query("DELETE FROM challenge_habits WHERE challengeId = :challengeId AND habitId = :habitId")
    suspend fun deleteChallengeHabit(challengeId: String, habitId: String)

    @Query("DELETE FROM challenge_habits WHERE challengeId = :challengeId")
    suspend fun deleteAllChallengeHabits(challengeId: String)
}