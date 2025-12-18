package com.example.hubtrackerapp.domain.auth

import com.example.hubtrackerapp.presentation.screens.registration.model.HabitUi
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        registerUser: RegistrationDraft
    ): Boolean {
       return repository.register(registerUser)
    }
}