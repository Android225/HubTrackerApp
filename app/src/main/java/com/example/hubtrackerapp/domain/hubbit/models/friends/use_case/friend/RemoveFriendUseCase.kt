package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.friend

import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class RemoveFriendUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(userId: String, friendId: String) {
        repository.removeFriend(userId, friendId)
    }
}