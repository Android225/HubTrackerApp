package com.example.hubtrackerapp.domain.auth

import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import javax.inject.Inject

class RegisterUseCase @Inject constructor (
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        registerUser: RegistrationDraft
    ): Boolean {
       return repository.register(registerUser)
    }
}