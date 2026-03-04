package com.example.hubtrackerapp.data.db.dao.club

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.hubtrackerapp.data.db.model.club.UserInClubDBModel
import com.example.hubtrackerapp.domain.hubbit.models.club.model.RoleMode

@Dao
interface UserInClubDao {

    @Insert
    suspend fun insertUserInClub(userInClub: UserInClubDBModel)

    @Delete
    suspend fun deleteUserInClub(userInClub: UserInClubDBModel)

    @Query("DELETE FROM user_in_club WHERE clubId = :clubId AND userId = :userId")
    suspend fun removeMember(clubId: String, userId: String)

    @Query("SELECT * FROM user_in_club WHERE clubId = :clubId")
    suspend fun getClubMembers(clubId: String): List<UserInClubDBModel>

    @Query("SELECT * FROM user_in_club WHERE userId = :userId")
    suspend fun getUserClubs(userId: String): List<UserInClubDBModel>

    @Query("SELECT EXISTS(SELECT 1 FROM user_in_club WHERE userId = :userId AND clubId = :clubId)")
    suspend fun isUserInClub(userId: String, clubId: String): Boolean

    @Query("UPDATE user_in_club SET role = :newRole WHERE clubId = :clubId AND userId = :userId")
    suspend fun updateMemberRole(clubId: String, userId: String, newRole: RoleMode)

    @Query("SELECT role FROM user_in_club WHERE clubId = :clubId AND userId = :userId")
    suspend fun getUserRole(clubId: String, userId: String): RoleMode?

    @Query("SELECT COUNT(*) FROM user_in_club WHERE clubId = :clubId")
    suspend fun getMembersCount(clubId: String): Int
}