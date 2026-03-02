package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.requests

import com.example.hubtrackerapp.domain.hubbit.models.friends.model.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest
import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class GetIncomingRequestsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {

    suspend operator fun invoke (): List<FriendRequest>
    {
        return repository.getIncomingRequests()
    }
}