package com.example.hubtrackerapp.domain.auth

class RegisterUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Boolean {
       return repository.register(email, password)
    }
}