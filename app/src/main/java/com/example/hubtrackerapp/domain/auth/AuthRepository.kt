package com.example.hubtrackerapp.domain.auth

import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft

interface AuthRepository {

    suspend fun login(email: String, password: String): Boolean

    suspend fun register(
        registerUser: RegistrationDraft
    ): Boolean
}