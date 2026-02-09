package com.example.hubtrackerapp.data.firebase

import android.util.Log
import com.example.hubtrackerapp.data.db.model.HabitDbModel
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.data.db.model.UserDbModel
import com.example.hubtrackerapp.data.firebase.model.FirebaseHabit
import com.example.hubtrackerapp.data.firebase.model.FirebaseHabitProgress
import com.example.hubtrackerapp.data.firebase.model.FirebaseUser
import com.example.hubtrackerapp.data.mapper.toDbModel
import com.example.hubtrackerapp.data.mapper.toFirebase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepository @Inject constructor() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = Firebase.firestore

    // коллекции
    private val usersCollection = firestore.collection("users")

    private fun userHabitsCollection(userId: String) =
        usersCollection.document(userId).collection("habits")

    private fun habitProgressCollection(userId: String, habitId: String) =
        userHabitsCollection(userId).document(habitId).collection("progress")

    // auth

    suspend fun registerWithEmail(
        email: String,
        password: String,
        userData: UserDbModel
    ): Result<String> = try {
        Log.d("FirebaseRepository", "Starting registration for email: $email")

        Log.d("FirebaseRepository", "Creating user in Firebase Auth...")
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUserId = authResult.user?.uid ?: throw Exception("Firebase не вернул UID")

        Log.d("FirebaseRepository", "Firebase Auth user created with ID: $firebaseUserId")

        // сохраняем данные в Firestore UID
        Log.d("FirebaseRepository", "Preparing user data for Firestore...")
        val firebaseUser = userData.copy(userId = firebaseUserId).toFirebase()
        Log.d("FirebaseRepository", "User data to save: $firebaseUser")

        Log.d("FirebaseRepository", "Saving to Firestore at path: users/$firebaseUserId")
        usersCollection.document(firebaseUserId).set(firebaseUser).await()

        // проверка, что данные сохранились
        Log.d("FirebaseRepository", "Verifying data saved in Firestore...")
        val savedDoc = usersCollection.document(firebaseUserId).get().await()
        if (savedDoc.exists()) {
            Log.d("FirebaseRepository", "User document successfully saved and verified in Firestore")
            val savedData = savedDoc.toObject(FirebaseUser::class.java)
            Log.d("FirebaseRepository", "Saved data: $savedData")
        } else {
            Log.e("FirebaseRepository", "ERROR: User document NOT FOUND in Firestore after save!")
        }

        Result.success(firebaseUserId)
    } catch (e: FirebaseAuthWeakPasswordException) {
        Log.e("FirebaseRepository", "Weak password: ${e.message}")
        Result.failure(Exception("Пароль слишком слабый"))
    } catch (e: FirebaseAuthUserCollisionException) {
        Log.e("FirebaseRepository", "User collision: ${e.message}")
        Result.failure(Exception("Пользователь с таким email уже существует"))
    } catch (e: Exception) {
        Log.e("FirebaseRepository", "Registration failed: ${e.message}", e)
        Result.failure(e)
    }

    suspend fun loginWithEmail(email: String, password: String): Result<String> = try {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw Exception("User ID is null")
        Result.success(userId)
    } catch (e: FirebaseAuthInvalidCredentialsException) {
        Result.failure(Exception("Неверный email или пароль"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    fun getCurrentUserId(): String? = auth.currentUser?.uid

    suspend fun logout() {
        auth.signOut()
    }

    // синхронизация данных

    suspend fun syncFromFirebase(userId: String): SyncResult {
        return try {
            // Данные пользователя
            val userDoc = usersCollection.document(userId).get().await()
            val firebaseUser = userDoc.toObject(FirebaseUser::class.java)

            // Все привычки
            val habitsSnapshot = userHabitsCollection(userId).get().await()
            val habits = habitsSnapshot.documents.mapNotNull { doc ->
                doc.toObject(FirebaseHabit::class.java)?.toDbModel()
            }

            // Весь прогресс для всех привычек
            val allProgress = mutableListOf<HabitProgressDbModel>()

            habits.forEach { habit ->
                val progressSnapshot = habitProgressCollection(userId, habit.habitId).get().await()
                val progressList = progressSnapshot.documents.mapNotNull { doc ->
                    doc.toObject(FirebaseHabitProgress::class.java)?.toDbModel()
                }
                allProgress.addAll(progressList)
            }

            SyncResult(
                user = firebaseUser?.toDbModel(),
                habits = habits,
                progressList = allProgress,
                isSuccess = true
            )
        } catch (e: Exception) {
            SyncResult(isSuccess = false, error = e.message ?: "Unknown error")
        }
    }

    suspend fun syncToFirebase(
        userId: String,
        user: UserDbModel? = null,
        habits: List<HabitDbModel>? = null,
        progressList: List<HabitProgressDbModel>? = null
    ): Boolean {
        return try {
            // Пользователь
            user?.let {
                usersCollection.document(userId)
                    .set(it.toFirebase())
                    .await()
            }

            // Привычки
            habits?.forEach { habit ->
                userHabitsCollection(userId)
                    .document(habit.habitId)
                    .set(habit.toFirebase())
                    .await()
            }

            // Прогресс
            progressList?.groupBy { it.habitId }?.forEach { (habitId, progressList) ->
                progressList.forEach { progress ->
                    habitProgressCollection(userId, habitId)
                        .document(progress.date.toString())
                        .set(progress.toFirebase())
                        .await()
                }
            }

            true
        } catch (e: Exception) {
            false
        }
    }

    // доп операции

    suspend fun addOrUpdateHabit(userId: String, habit: HabitDbModel) {
        userHabitsCollection(userId)
            .document(habit.habitId)
            .set(habit.toFirebase())
            .await()
    }

    suspend fun deleteHabit(userId: String, habitId: String) {
        userHabitsCollection(userId)
            .document(habitId)
            .update(
                mapOf(
                    "isArchived" to true,
                    "lastUpdated" to FieldValue.serverTimestamp()
                )
            )
            .await()
    }

    suspend fun updateProgress(userId: String, progress: HabitProgressDbModel) {
        habitProgressCollection(userId, progress.habitId)
            .document(progress.date.toString())
            .set(progress.toFirebase())
            .await()
    }

    data class SyncResult(
        val user: UserDbModel? = null,
        val habits: List<HabitDbModel> = emptyList(),
        val progressList: List<HabitProgressDbModel> = emptyList(),
        val isSuccess: Boolean = false,
        val error: String? = null
    )
}