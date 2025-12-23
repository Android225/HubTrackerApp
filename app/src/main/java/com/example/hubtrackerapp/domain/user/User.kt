package com.example.hubtrackerapp.domain.user

data class User(
    val userId: String,
    val email: String,
    val password: String = "",
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String
    )