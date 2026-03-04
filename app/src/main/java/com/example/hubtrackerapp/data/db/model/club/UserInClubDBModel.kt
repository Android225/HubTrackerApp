package com.example.hubtrackerapp.data.db.model.club

import androidx.room.Entity
import com.example.hubtrackerapp.domain.hubbit.models.club.model.RoleMode

@Entity(
    tableName = "user_in_club",
    primaryKeys = ["userId", "clubId"]
)
data class UserInClubDBModel(
    val userId: String,
    val clubId: String,
    val role: RoleMode,
    val joinedAt: Long
)