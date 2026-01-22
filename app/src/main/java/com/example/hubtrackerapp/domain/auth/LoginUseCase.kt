package com.example.hubtrackerapp.domain.auth

import javax.inject.Inject

class LoginUseCase @Inject constructor (
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.login(email, password)
    }
}