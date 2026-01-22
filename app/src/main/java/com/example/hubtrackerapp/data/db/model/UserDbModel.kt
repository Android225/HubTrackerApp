package com.example.hubtrackerapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserDbModel(
    @PrimaryKey
    val userId: String,
    val email: String,
    val password: String = "",
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String
    )