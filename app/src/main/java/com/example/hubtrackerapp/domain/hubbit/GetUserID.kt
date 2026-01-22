package com.example.hubtrackerapp.domain.hubbit

import javax.inject.Inject

class GetUserID @Inject constructor (
    private val repository: HabitRepository
) {
    suspend operator fun invoke() : String{
        return repository.getUserId()
    }
}