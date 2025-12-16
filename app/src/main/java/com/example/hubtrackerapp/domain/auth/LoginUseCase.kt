package com.example.hubtrackerapp.domain.auth

class LoginUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Boolean {
        return repository.login(email, password)
    }
}