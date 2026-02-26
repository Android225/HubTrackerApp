package com.example.hubtrackerapp.domain.repository

import com.example.hubtrackerapp.domain.hubbit.models.friends.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.FriendRequest
import com.example.hubtrackerapp.domain.hubbit.models.friends.RequestStatus

interface FriendsRepository {

    // ---FriendRequest---//

    // заявка дружбы
    suspend fun sendFriendRequest(toUserId: String,message: String?): Result<FriendRequest>

    //получение входящих заявок дружбы
    suspend fun getIncomingRequests(): List<FriendRequest>  // status = PENDING, toUserId = текущий

    // получение отправленных заявок дружбы
    suspend fun getOutgoingRequests(): List<FriendRequest>  // status = PENDING, fromUserId = текущий

    // принять заявку
    suspend fun acceptFriendRequest(requestId: String): Result<Friend>

    // отклонить
    suspend fun declineFriendRequest(requestId: String)

    // отменить если пользователь отправлял
    suspend fun cancelFriendRequest(requestId: String)

    // получение заявки по айди
    suspend fun getRequestById(requestId: String): FriendRequest?

    // проверка на возможность отправки:
    //  самому себе
    //  если уже есть активная заявка
    //  если уже друзья
    suspend fun canSendRequest(toUserId: String): Boolean

    // история всех заявок текущего пользователя
    suspend fun getAllRequestsForUser(): List<FriendRequest>

    suspend fun deleteRequest(requestId: String): Boolean

    // ---Friend--- //

    // Получить список друзей
    suspend fun getFriends(): List<Friend>

    // Удалить из друзей
    suspend fun removeFriend(friendId: String)

    // является ли otherUserId другом(лежит ли в таблице Friend)
    suspend fun areFriends(userId: String, otherUserId: String): Boolean
}