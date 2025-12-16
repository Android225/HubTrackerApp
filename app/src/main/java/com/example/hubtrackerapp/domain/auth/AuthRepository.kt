package com.example.hubtrackerapp.domain.auth

interface AuthRepository {

    fun login(email: String,password: String): Boolean

    fun register(email: String,password: String): Boolean
}