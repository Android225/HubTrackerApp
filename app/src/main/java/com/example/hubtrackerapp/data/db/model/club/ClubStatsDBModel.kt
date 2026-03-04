package com.example.hubtrackerapp.data.db.model.club

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "club_stats")
data class ClubStatsDBModel(
    @PrimaryKey
    val clubId: String,
    val totalCompletions: Int = 0,
    val totalChallenges: Int = 0,
    val currentChallengeId: String? = null,
    val lastUpdated: Long
)