package com.example.hubtrackerapp.data.db.model.statistic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType

@Entity(tableName = "user_action")
data class UserActionDBModel(
    @PrimaryKey
    val userActionId: String,
    val userId: String,
    val actionType: ActionType,
    val entityId: String? = null,
    val entityName: String? = null,
    val pointsEarned: Int = 0,
    val timestamp: Long,
    val additionalData: String? = null
)