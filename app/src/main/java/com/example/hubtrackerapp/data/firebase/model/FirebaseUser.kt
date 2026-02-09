package com.example.hubtrackerapp.data.firebase.model

data class FirebaseUser(
    val userId: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "", //"2000-01-01"
    val gender: String = "",
    val createdAt: Long = System.currentTimeMillis() // Timestamp создания
)