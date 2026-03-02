package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.friend

import com.example.hubtrackerapp.domain.hubbit.models.friends.model.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class GetFriendsUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(): List<Friend> {
        return repository.getFriends()
    }
}