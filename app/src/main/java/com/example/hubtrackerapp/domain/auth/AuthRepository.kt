package com.example.hubtrackerapp.domain.auth

import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft

interface AuthRepository {

    fun login(email: String, password: String): Boolean

    fun register(
        registerUser: RegistrationDraft
    ): Boolean
}