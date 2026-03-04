package com.example.hubtrackerapp.data.db.model.club

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clubs")
data class ClubDBModel(
    @PrimaryKey
    val clubId: String,
    val adminId: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val createdAt: Long,
    val isPrivate: Boolean,
    val lastUpdate: Long,
    val memberCount: Int = 0
)