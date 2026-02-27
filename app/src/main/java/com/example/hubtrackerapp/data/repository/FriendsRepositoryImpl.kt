package com.example.hubtrackerapp.data.repository

import android.util.Log
import com.example.hubtrackerapp.data.db.dao.friends.FriendRequestDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.dao.friends.FriendDao
import com.example.hubtrackerapp.data.db.model.friends.FriendDBModel
import com.example.hubtrackerapp.data.db.model.friends.FriendRequestDBModel
import com.example.hubtrackerapp.data.firebase.FirebaseRepository
import com.example.hubtrackerapp.data.mapper.friends.toFriendEntity
import com.example.hubtrackerapp.data.mapper.friends.toFriendRequestEntity
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.Friend
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.FriendRequest
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.RequestStatus
import com.example.hubtrackerapp.domain.hubbit.models.friends.repository.FriendsRepository
import java.util.UUID
import javax.inject.Inject

class FriendsRepositoryImpl @Inject constructor(
    private val friendRequestDao: FriendRequestDao,
    private val friendDao: FriendDao,
    private val userDao: UserDao,
    private val firebaseRepository: FirebaseRepository
) : FriendsRepository {
    override suspend fun sendFriendRequest(
        toUserId: String,
        message: String?
    ): Result<FriendRequest> {
        return try {
            //проверка
            if (!canSendRequest(toUserId)) {
                return Result.failure(Exception("Cannot send request"))
            }
            
            val currentUser = userDao.getCurrentUser()
                ?: return Result.failure(Exception("User not found"))

            val actualTime = System.currentTimeMillis()

            val request = FriendRequestDBModel(
                requestId = UUID.randomUUID().toString(),
                fromUserId = currentUser.userId,
                toUserId = toUserId,
                fromUserFirstName = currentUser.firstName,
                fromUserLastName = currentUser.lastName,
                fromUserImageUrl = null, // Позже в релизе прикрутить  картинки
                status = RequestStatus.PENDING,
                message = message,
                createdAt = actualTime,
                updatedAt = actualTime
            )
            //в Room
            friendRequestDao.insertRequest(request)

            //отправляем в FIREBASE
            //friendRequestDao.insertRequest(request)

            Result.success(request.toFriendRequestEntity())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getIncomingRequests(): List<FriendRequest> {
        val currentUserId = userDao.getUserId()
            ?: return emptyList()
        return friendRequestDao.getIncomingRequests(currentUserId)
            .map { it.toFriendRequestEntity() }
    }

    override suspend fun getOutgoingRequests(): List<FriendRequest> {
        val currentUserId = userDao.getUserId()
            ?: return emptyList()
        return friendRequestDao.getOutgoingRequests(currentUserId)
            .map { it.toFriendRequestEntity() }
    }

    override suspend fun acceptFriendRequest(requestId: String): Result<Friend> {
        return try {
            val request = friendRequestDao.getRequestById(requestId)
                ?: return Result.failure(Exception("Request not found"))

            if (request.status != RequestStatus.PENDING){
                return Result.failure(Exception("Request already processed"))
            }

            val currentUser = userDao.getCurrentUser()
                ?: return Result.failure(Exception("current user not found"))

            val otherUser = firebaseRepository.getUserById(request.fromUserId)
                ?: return Result.failure(Exception("Other user not found in Firebase"))

            val actualTime = System.currentTimeMillis()


            // запись для текущего пользователя
            val friendForCurrentUser = FriendDBModel(
                userId = currentUser.userId,
                friendId = otherUser.userId,
                friendFirstName = otherUser.firstName,
                friendLastName = otherUser.lastName,
                friendImageUrl = null, // ПЕРЕД РЕЛИЗОМ ПРИКРУТИТЬ КАРТИНКИ
                points = 52, //ПОЗЖЕ ПОДТЯНУТЬ СТАТИСТИКУ!
                createdAt = actualTime
            )

            //запись для user кто отправил заявку
            val friendForOtherUser = FriendDBModel(
                userId = otherUser.userId,
                friendId = currentUser.userId,
                friendFirstName = currentUser.firstName,
                friendLastName = currentUser.lastName,
                friendImageUrl = null,
                points = 52,
                createdAt = actualTime
            )

            // сохраняем в Room
            friendDao.insert(friendForCurrentUser)
            // *ПЕРЕДЕЛАТЬ* Сохраняем в FIREBASE 2 записи
            //friendDao.insert(friendForCurrentUser)
            friendDao.insert(friendForOtherUser)

            // обновляем в Room
            friendRequestDao.updateRequestStatus(
                requestId = requestId,
                status = RequestStatus.ACCEPTED,
                updatedAt = actualTime
            )

            // обновляем в FIREBASE!
//            friendRequestDao.updateRequestStatus(
//                requestId = requestId,
//                status = RequestStatus.ACCEPTED,
//                updatedAt = actualTime
//            )

            Result.success(friendForCurrentUser.toFriendEntity())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun declineFriendRequest(requestId: String) {
        try {
            val actualTime = System.currentTimeMillis()
            //записываем в Room
            friendRequestDao.updateRequestStatus(
                requestId = requestId,
                status = RequestStatus.DECLINED,
                updatedAt = actualTime
            )
            //записываем в Firebase
//            friendRequestDao.updateRequestStatus(
//                requestId = requestId,
//                status = RequestStatus.DECLINED,
//                updatedAt = actualTime
//            )

        } catch (e: Exception) {
            Log.d("FriendsRepository", "error for declineFriendRequest for request: $requestId")
        }
    }

    // отмена отправленной заявки
    override suspend fun cancelFriendRequest(requestId: String) {
        try {
            //УДАЛЯЕМ В ROOM
            friendRequestDao.deleteRequestById(requestId)
            //УУДАЛЯЕМ И В FIREBASE
//            friendRequestDao.deleteRequestById(requestId)

        } catch (e: Exception) {
            Log.d("FriendsRepository", "error for cancelFriendRequest for request: $requestId")
        }
    }

    override suspend fun getRequestById(requestId: String): FriendRequest? {
        return friendRequestDao.getRequestById(requestId)?.toFriendRequestEntity()
    }

    override suspend fun canSendRequest(toUserId: String): Boolean {
        val currentUserId = userDao.getCurrentUser()?.userId ?: return false

        //самому себе
        if (currentUserId == toUserId) return false

        //уже друзья?
        if (areFriends(currentUserId,toUserId)) return false


        //мб объеденить в общий запрос hasPendingRequest
        // ПРОВЕРКУ ВЕСТИ ЧЕРЕЗ FIREBASE !!!!
        val requestFrom = friendRequestDao.getPendingRequestBetweenUsers(currentUserId,toUserId)
        val requestTo = friendRequestDao.getPendingRequestBetweenUsers(toUserId,currentUserId)
        return !(requestFrom != null || requestTo!= null)
    }

    //история запросов User
    override suspend fun getAllRequestsForUser(): List<FriendRequest> {
        val currentUserId = userDao.getUserId() ?: return emptyList()

        //мб синхронизаци с FIREBASE на всякий

        return friendRequestDao.getAllUserRequests(currentUserId)
            .map { it.toFriendRequestEntity() }
    }

    override suspend fun deleteRequest(requestId: String): Boolean = try {
        //полное удаление в Room
        friendRequestDao.deleteRequestById(requestId)

        //полное удаление в Firebase
        //friendRequestDao.deleteRequestById(requestId)
        true
    } catch (e: Exception) {
        Log.d("FriendRepository","deleteRequest delete is false")
        false
    }

    override suspend fun getFriends(): List<Friend> {
        //ПРИКРУТИТЬ FIREBASE + Синхронизация записей друзей


        val currentUserId = userDao.getUserId() ?: return emptyList()
        return friendDao.getFriends(currentUserId)
            .map { it.toFriendEntity() }

    }

    override suspend fun removeFriend(userId: String,friendId: String) {
        try {
            //удаление в Room
            friendDao.removeFriend(userId, friendId)

            // удаление в FIREBASE
           // friendDao.removeFriend(userId, friendId)
            friendDao.removeFriend(friendId, userId)
        }catch (e: Exception){
            Log.d("FriendRepository","error for remove friend in removeFriend")
        }
    }

    override suspend fun areFriends(
        userId: String,
        otherUserId: String
    ): Boolean {
        //мб тоже синхронизацию актуальной список друзей иметь
        return friendDao.areFriends(userId,otherUserId)
    }

    override suspend fun getFriendsCount(userId: String): Int {
        //мб тоже синхронизацию актуальной список друзей иметь
        return friendDao.getFriendsCount(userId)
    }
}