package com.example.hubtrackerapp.domain.hubbit.models.club

import com.example.hubtrackerapp.domain.hubbit.models.statistic.ActionType

data class ClubFeed (
    val clubFeedId: String,
    val clubId: String,

    val userId: String,
    val userName: String,
    val userImageUrl: String? = null,

    val actionType: ActionType,
    val message: String,

    val entityId: String? = null,
    val entityName: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)