package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.requests

import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class CanSendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(toUserId: String): Boolean {
        return repository.canSendRequest(toUserId)
    }
}