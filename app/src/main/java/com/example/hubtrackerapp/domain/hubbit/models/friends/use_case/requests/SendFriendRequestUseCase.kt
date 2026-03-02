package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.requests

import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest
import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val repository: FriendsRepository
) {

    suspend operator fun invoke (
        toUserId: String,message: String? = null
    ): Result<FriendRequest> {
        return repository.sendFriendRequest(toUserId,message)
    }
}