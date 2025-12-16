package com.example.hubtrackerapp.data

import com.example.hubtrackerapp.domain.auth.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override fun login(email: String, password: String): Boolean {
        return email == "Admin@Admin" && password == "Admin"
    }

    override fun register(email: String, password: String): Boolean {
        return true
    }
}