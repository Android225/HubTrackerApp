package com.example.hubtrackerapp.domain.hubbit

import com.example.hubtrackerapp.domain.user.User
import javax.inject.Inject

class GetUserCardUseCase @Inject constructor (
    private val repository: HabitRepository
) {
    suspend operator fun invoke (
        userId: String
    ): User {
        return repository.getUserCard(userId = userId)
    }
}