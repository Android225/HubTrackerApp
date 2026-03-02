package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.friend

import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class AreFriendsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(userId: String, otherUserId: String): Boolean {
        return repository.areFriends(userId, otherUserId)
    }
}