package com.example.hubtrackerapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.UserDbModel

@Dao
interface UserDao {

    @Query("SELECT userId FROM user LIMIT 1")
    suspend fun getUserId(): String?

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getCurrentUser(): UserDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userDbModel: UserDbModel)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun authenticate(email: String, password: String): UserDbModel?

    //при выходе из профиля очистка таблицы
    @Query("DELETE FROM user")
    suspend fun clearUsers()

//    // занят ли email (для регистрации)
//    @Query("SELECT COUNT(*) FROM user WHERE email = :email")
//    suspend fun isEmailTaken(email: String): Int // 0 или 1
}