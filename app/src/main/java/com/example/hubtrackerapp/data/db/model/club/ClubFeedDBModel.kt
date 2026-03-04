package com.example.hubtrackerapp.data.db.model.club

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType

@Entity(tableName = "club_feed")
data class ClubFeedDBModel(
    @PrimaryKey
    val clubFeedId: String,
    val clubId: String,
    val userId: String,
    val userName: String,
    val userImageUrl: String? = null,
    val actionType: ActionType,
    val message: String,
    val entityId: String? = null,
    val entityName: String? = null,
    val timestamp: Long
)