package com.example.hubtrackerapp.domain.hubbit.models.friends.use_case.friend

import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import javax.inject.Inject

class GetFriendsCountUseCase @Inject constructor(
    private val repository: FriendsRepository
) {
    suspend operator fun invoke(userId: String): Int {
        return repository.getFriendsCount(userId)
    }
}