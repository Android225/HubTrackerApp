package com.example.hubtrackerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
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