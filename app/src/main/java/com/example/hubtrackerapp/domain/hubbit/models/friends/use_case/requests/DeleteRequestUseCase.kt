package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.requests

import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest
import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class DeleteRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(requestId: String): Boolean {
        return repository.deleteRequest(requestId)
    }
}