package com.example.hubtrackerapp.data

//import com.example.hubtrackerapp.data.TempDB.testUser
import HabitMetric
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.data.db.dao.HabitDao
import com.example.hubtrackerapp.data.db.dao.HabitProgressDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.data.firebase.FirebaseRepository
import com.example.hubtrackerapp.data.mapper.toDbModel
import com.example.hubtrackerapp.data.mapper.toDbModelWithoutUserId
import com.example.hubtrackerapp.data.mapper.toHabitDbModelWithoutHabitId
import com.example.hubtrackerapp.data.mapper.toHabitEntities
import com.example.hubtrackerapp.data.mapper.toHabitEntity
import com.example.hubtrackerapp.data.mapper.toHabitProgressEntity
import com.example.hubtrackerapp.data.mapper.toUserEntity
import com.example.hubtrackerapp.domain.auth.AuthRepository
import com.example.hubtrackerapp.domain.hubbit.HabitRepository
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.hubbit.models.isActive
import com.example.hubtrackerapp.domain.user.User
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val userDao: UserDao,
    private val habitProgressDao: HabitProgressDao,
    private val firebaseRepository: FirebaseRepository

) : HabitRepository, AuthRepository {

    private val syncScope = CoroutineScope(Dispatchers.IO)
    var isSyncEnabled: Boolean = true
        private set

// ========== АУТЕНТИФИКАЦИЯ (ИСПРАВЛЕННАЯ) ==========

    override suspend fun register(registerUser: RegistrationDraft): Boolean {
        return try {
            Log.d("Repository", "Starting registration process...")

            // 1. Очистка локальной БД
            clearLocalDatabase()

            // 2. Создаем UserDbModel БЕЗ userId и БЕЗ password
            val userWithoutId = registerUser.toDbModelWithoutUserId()

            // 3. Регистрируем в Firebase
            Log.d("Repository", "Registering in Firebase...")
            val firebaseResult = withContext(Dispatchers.IO) {
                firebaseRepository.registerWithEmail(
                    email = registerUser.email,
                    password = registerUser.password,
                    userData = userWithoutId
                )
            }

            if (firebaseResult.isFailure) {
                Log.e("Repository", "Firebase registration failed: ${firebaseResult.exceptionOrNull()}")
                return false
            }

            val firebaseUserId = firebaseResult.getOrThrow()
            Log.d("Repository", "Firebase registration successful, userId: $firebaseUserId")

            // 4. Сохраняем в локальную Room С Firebase UID
            val userWithFirebaseId = userWithoutId.copy(userId = firebaseUserId)
            Log.d("Repository", "Saving user to local DB: $userWithFirebaseId")
            userDao.insertUser(userWithFirebaseId)

            // 5. Проверяем, что пользователь действительно сохранен
            val savedUser = userDao.getCurrentUser()
            if (savedUser != null) {
                Log.d("Repository", "User successfully saved to local DB: $savedUser")
            } else {
                Log.e("Repository", "Failed to save user to local DB")
                return false
            }

            // 6. Добавляем предопределенные привычки, которые пользователь выбрал
            Log.d("Repository", "Adding ${registerUser.habbies.size} predefined habits")
            registerUser.habbies.forEach { predefinedHabit ->
                try {
                    val habit = HabitUi(
                        habitId = UUID.randomUUID().toString(),
                        userId = firebaseUserId,
                        emoji = predefinedHabit.icon,
                        title = predefinedHabit.habitName,
                        createdAt = LocalDate.now(),
                        schedule = predefinedHabit.habitSchedule,
                        color = predefinedHabit.color,
                        target = predefinedHabit.target.toString(),
                        metric = predefinedHabit.metricForHabit,
                        reminderTime = predefinedHabit.reminderTime,
                        reminderDate = predefinedHabit.reminderDate,
                        reminderIsActive = predefinedHabit.reminderIsActive,
                        habitType = predefinedHabit.habitType,
                        habitCustom = false
                    ).toDbModel()

                    Log.d("Repository", "Adding habit: ${habit.title}")
                    habitDao.addHabit(habit)

                    // Синхронизируем с Firebase
                    if (isSyncEnabled) {
                        syncScope.launch {
                            try {
                                firebaseRepository.addOrUpdateHabit(firebaseUserId, habit)
                                Log.d("Repository", "Habit synced to Firebase: ${habit.title}")
                            } catch (e: Exception) {
                                Log.e("Repository", "Failed to sync habit to Firebase: ${e.message}")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Repository", "Error adding habit ${predefinedHabit.habitName}: ${e.message}")
                }
            }

            Log.d("Repository", "Registration completed successfully")
            true
        } catch (e: Exception) {
            Log.e("Repository", "Registration failed with exception: ${e.message}", e)
            false
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        Log.d("Repository", "Login attempt for email: $email")
        return try {
            // 1. Firebase аутентификация (Firebase Auth проверяет пароль)
            Log.d("Repository", "Attempting Firebase login...")
            val firebaseResult = firebaseRepository.loginWithEmail(email, password)
            if (firebaseResult.isFailure) {
                Log.e("Repository", "Firebase login failed: ${firebaseResult.exceptionOrNull()}")
                return false
            }

            val firebaseUserId = firebaseResult.getOrThrow()
            Log.d("Repository", "Firebase login successful, userId: $firebaseUserId")
            // 2. Очистка локальной БД
            clearLocalDatabase()

            // 3. Загрузка ВСЕХ данных из Firebase
            Log.d("Repository", "Syncing from Firebase...")
            val syncResult = firebaseRepository.syncFromFirebase(firebaseUserId)
            Log.d("Repository", "Firebase sync result: success=${syncResult.isSuccess}, user=${syncResult.user?.email}, habits=${syncResult.habits.size}, progress=${syncResult.progressList.size}")
            if (!syncResult.isSuccess) {
                Log.e("Repository", "Firebase sync failed")
                return false
            }

            // 4. Сохраняем в локальную Room (БЕЗ пароля!)
            syncResult.user?.let { user ->
                Log.d("Repository", "Saving user to local DB: ${user.email}")
                // Сохраняем пользователя как есть (без пароля)
                userDao.insertUser(user)
            }
            Log.d("Repository", "Saving ${syncResult.habits.size} habits to local DB")
            syncResult.habits.forEach { habitDao.addHabit(it) }
            Log.d("Repository", "Saving ${syncResult.progressList.size} progress records to local DB")
            syncResult.progressList.forEach { habitProgressDao.saveProgress(it) }

            val savedUser = userDao.getCurrentUser()
            Log.d("Repository", "After login, user in local DB: ${savedUser?.email ?: "null"}")
            true
        } catch (e: Exception) {
            Log.e("Repository", "Login failed with exception: ${e.message}", e)
            false
        }
    }

    // ========== ОПЕРАЦИИ С ПРИВЫЧКАМИ ==========

    override suspend fun addHabit(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule,
        color: Color,
        target: String,
        metric: HabitMetric,
        reminderTime: LocalTime,
        reminderDate: HabitSchedule,
        reminderIsActive: Boolean,
        habitType: ModeForSwitchInHabit,
        habitCustom: Boolean
    ) {
        val userId = getUserId()

        // 1. Создаем HabitUi → HabitDbModel
        val habit = HabitUi(
            habitId = UUID.randomUUID().toString(),
            userId = userId,
            emoji = emoji,
            title = title,
            createdAt = createdAt,
            schedule = schedule,
            color = color,
            target = target,
            metric = metric,
            reminderTime = reminderTime,
            reminderDate = reminderDate,
            reminderIsActive = reminderIsActive,
            habitType = habitType,
            habitCustom = habitCustom
        ).toDbModel() // Используем твой существующий маппер!

        // 2. Сохраняем локально
        habitDao.addHabit(habit)

        // 3. Синхронизируем с Firebase
        if (isSyncEnabled) {
            syncScope.launch {
                firebaseRepository.addOrUpdateHabit(userId, habit)
            }
        }
    }

    override suspend fun editHabit(habit: HabitUi) {
        val dbModel = habit.toDbModel() // Используем твой маппер

        // 1. Локально
        habitDao.addHabit(dbModel)

        // 2. Firebase
        if (isSyncEnabled) {
            syncScope.launch {
                firebaseRepository.addOrUpdateHabit(habit.userId, dbModel)
            }
        }
    }

    override suspend fun deleteHabit(habitId: String) {
        val userId = getUserId()

        // 1. Локально (архивация)
        habitDao.deleteHabit(habitId)

        // 2. Firebase
        if (isSyncEnabled) {
            syncScope.launch {
                firebaseRepository.deleteHabit(userId, habitId)
            }
        }
    }

    // ========== ОПЕРАЦИИ С ПРОГРЕССОМ ==========
    override suspend fun saveProgress(habitProgress: HabitProgress) {
        val dbModel = habitProgress.toDbModel() // Твой маппер

        // 1. Локально
        habitProgressDao.saveProgress(dbModel)

        // 2. Firebase
        if (isSyncEnabled) {
            syncScope.launch {
                firebaseRepository.updateProgress(getUserId(), dbModel)
            }
        }
    }

    override suspend fun switchCompleteStatus(habitId: String, date: LocalDate) {
        // 1. Локально
        habitProgressDao.switchCompleteStatus(habitId, date)

        // 2. Получаем обновленный прогресс
        val progress = habitProgressDao.getProgress(habitId, date)

        // 3. Синхронизируем
        progress?.let {
            if (isSyncEnabled) {
                syncScope.launch {
                    firebaseRepository.updateProgress(getUserId(), it)
                }
            }
        }
    }
    override suspend fun switchFailedStatus(habitId: String, date: LocalDate) {
        // 1. Локально
        habitProgressDao.switchFailedStatus(habitId, date)

        // 2. Получаем обновленный прогресс
        val progress = habitProgressDao.getProgress(habitId, date)

        // 3. Синхронизируем
        progress?.let {
            if (isSyncEnabled) {
                syncScope.launch {
                    firebaseRepository.updateProgress(getUserId(), it)
                }
            }
        }
    }
    override suspend fun switchSkipStatus(habitId: String, date: LocalDate) {
        // 1. Локально
        habitProgressDao.switchSkippedStatus(habitId, date)

        // 2. Получаем обновленный прогресс
        val progress = habitProgressDao.getProgress(habitId, date)

        // 3. Синхронизируем
        progress?.let {
            if (isSyncEnabled) {
                syncScope.launch {
                    firebaseRepository.updateProgress(getUserId(), it)
                }
            }
        }
    }

    // ========== СИНХРОНИЗАЦИЯ ==========
    suspend fun syncAllToFirebase(): Boolean {
        val userId = getUserId()

        return try {
            // 1. Получаем пользователя
            val user = userDao.getCurrentUser()

            // 2. Получаем ВСЕ привычки пользователя
            val habits = habitDao.getAllHabitsByUserId(userId)

            // 3. Получаем ВЕСЬ прогресс пользователя (используем новый метод!)
            val allProgress = habitProgressDao.getAllProgressForUser(userId)

            // 4. Отправляем ВСЁ в Firebase
            firebaseRepository.syncToFirebase(
                userId = userId,
                user = user,
                habits = habits,
                progressList = allProgress  // ← ВЕСЬ прогресс пользователя
            )
        } catch (e: Exception) {
            false
        }
    }

    suspend fun syncFromFirebase(): Boolean {
        val userId = getUserId()

        return try {
            val syncResult = firebaseRepository.syncFromFirebase(userId)

            if (syncResult.isSuccess) {
                // Очищаем локальную БД
                clearLocalDatabase()

                // Сохраняем новые данные
                syncResult.user?.let { userDao.insertUser(it) }
                syncResult.habits.forEach { habitDao.addHabit(it) }
                syncResult.progressList.forEach { habitProgressDao.saveProgress(it) }

                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserId(): String {
        // Сначала Firebase UID
        val firebaseUserId = firebaseRepository.getCurrentUserId()
        if (firebaseUserId != null) {
            return firebaseUserId
        }

        // Если не в Firebase, то из локальной Room
        return userDao.getUserId() ?: throw Exception("User not logged in")
    }

    suspend fun isUserLoggedIn(): Boolean {
        return firebaseRepository.getCurrentUserId() != null ||
                userDao.getUserId() != null
    }

    fun enableSync() { isSyncEnabled = true }
    fun disableSync() { isSyncEnabled = false }


    override suspend fun getHabitsWithScheduleForDate(
        userId: String,
        date: LocalDate
    ): Flow<List<HabitWithProgressUi>> {
        // Проверяем, что userId не пустой
        if (userId.isBlank()) {
            Log.e("Repository", "Empty userId in getHabitsWithScheduleForDate")
            return flowOf(emptyList())
        }

        return combine(
            habitDao.getActiveHabitsFlow(userId),
            habitProgressDao.getProgressForDateFlow(date)
        ) { habits, progresses ->
            Log.d("Repository", "Combining ${habits.size} habits with ${progresses.size} progresses")

            habits.toHabitEntities().filter {
                it.schedule.isActive(it.createdAt, date = date)
            }.map { habit ->
                val progress = getProgressForHabitInDate(habit.habitId, date)

                HabitWithProgressUi(
                    habitId = habit.habitId,
                    emoji = habit.emoji,
                    title = habit.title,
                    isCompleted = progress.isCompleted,
                    progress = progress.progress,
                    color = habit.color,
                    target = habit.target,
                    metric = habit.metric,
                    habitType = habit.habitType,
                    progressWithTarget = progress.progressWithTarget,
                    skipped = progress.skipped,
                    failed = progress.failed
                )
            }
        }
    }

    override suspend fun getHabit(
        userId: String,
        habitId: String
    ): HabitUi {
        return habitDao.getHabit(userId, habitId)?.toHabitEntity() // Используйте безопасный вызов
            ?: throw IllegalStateException("Habit not found: $habitId for user: $userId")
    }

//    override suspend fun changeSchedule(
//        habitId: String,
//        schedule: HabitSchedule
//    ) {
//
//    }

    override suspend fun addProgressForHabit(
        habitId: String,
        date: LocalDate
    ): HabitProgress {
        val progress = HabitProgress(
            habitId = habitId,
            date = date
        )
        habitProgressDao.saveProgress(progress.toDbModel())

        return progress
    }


    override suspend fun getProgressForHabitInDate(
        habitId: String,
        date: LocalDate
    ): HabitProgress {
        return getProgress(habitId, date) ?: addProgressForHabit(habitId, date)
    }

    override suspend fun getProgress(
        habitId: String,
        date: LocalDate
    ): HabitProgress? {
        return habitProgressDao.getProgress(habitId, date)?.toHabitProgressEntity()
    }


    override suspend fun getUserCard(userId: String): User {
        // Получаем пользователя из базы данных
        Log.d("Repository", "getUserCard called with userId: $userId")
        val userDbModel = userDao.getCurrentUser()

        Log.d("Repository", "userDao.getCurrentUser() returned: ${userDbModel?.email ?: "null"}")
        return if (userDbModel != null) {
            Log.d("Repository", "Found user in local DB: ${userDbModel.email}")
            userDbModel.toUserEntity()

        } else {
            // Если пользователя нет в локальной базе, пробуем получить из Firebase
            Log.w("Repository", "User not found in local DB for userId: $userId")
            try {
                val syncResult = firebaseRepository.syncFromFirebase(userId)
                Log.d("Repository", "Firebase sync result: success=${syncResult.isSuccess}, user=${syncResult.user?.email}")
                if (syncResult.isSuccess && syncResult.user != null) {
                    // Сохраняем в локальную базу
                    userDao.insertUser(syncResult.user!!)
                    Log.d("Repository", "User loaded from Firebase and saved locally")
                    syncResult.user!!.toUserEntity()
                } else {
                    throw IllegalStateException("User not found in Firebase or local DB")
                }
            } catch (e: Exception) {
                Log.e("Repository", "Failed to get user from Firebase: ${e.message}")
                throw IllegalStateException("Failed to get user data: ${e.message}")
            }
        }
    }

    suspend fun clearLocalDatabase() {
        try {
            Log.d("Repository", "Clearing database...")

            habitProgressDao.clearAllProgress()
            habitDao.clearHabits()
            userDao.clearUsers()

            Log.d("Repository", "Database cleared successfully")
        } catch (e: Exception) {
            Log.e("Repository", "Error clearing database: ${e.message}", e)
            throw e
        }
    }


//    companion object{
//        private val LOCK = Any()
//        private var instance: HabitsRepositoryImpl? = null
//
//        fun getInstance(context: Context): HabitsRepositoryImpl{
//            instance?.let { return it }
//
//            synchronized(LOCK){
//                instance?.let { return it }
//
//                return HabitsRepositoryImpl(context).also {
//                    instance = it
//                }
//            }
//        }
//    }
}