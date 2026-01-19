package com.example.hubtrackerapp.domain.hubbit

class GetUserID(
    private val repository: HabitRepository
) {
    suspend operator fun invoke() : String{
        return repository.getUserId()
    }
}