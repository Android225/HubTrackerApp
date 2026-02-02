package com.example.hubtrackerapp.data

//import com.example.hubtrackerapp.data.TempDB.testUser
import HabitMetric
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.data.db.dao.HabitDao
import com.example.hubtrackerapp.data.db.dao.HabitProgressDao
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.data.mapper.toDbModel
import com.example.hubtrackerapp.data.mapper.toDbModelWithoutUserId
import com.example.hubtrackerapp.data.mapper.toHabitDbModelWithoutHabitId
import com.example.hubtrackerapp.data.mapper.toHabitEntities
import com.example.hubtrackerapp.data.mapper.toHabitEntity
import com.example.hubtrackerapp.data.mapper.toHabitProgressEntity
import com.example.hubtrackerapp.data.mapper.toUserEntity
import com.example.hubtrackerapp.data.utils.PasswordHasher.hashPassword
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

class HabitsRepositoryImpl @Inject constructor (
    private val habitDao: HabitDao,
    private val userDao: UserDao,
    private val habitProgressDao: HabitProgressDao

): HabitRepository, AuthRepository {

//    private val habitsDatabase = HabitsDatabase.getInstance(context)
//    private val habitDao = habitsDatabase.habitDao()
//    private val userDao = habitsDatabase.userDao()
//    private val habitProgressDao = habitsDatabase.habitProgressDao()

    override suspend fun getHabitsWithScheduleForDate(
        userId: String,
        date: LocalDate
    ): Flow<List<HabitWithProgressUi>> {

        return combine(
            habitDao.getActiveHabitsFlow(userDao.getUserId()!!), // плохой вызов потом добавить обрабочики
                    habitProgressDao.getProgressForDateFlow(date)
        ){habits, progresses ->
            habits.toHabitEntities().filter {
                it.schedule.isActive(it.createdAt, date = date)
            }.map { habit ->
                val progress = getProgressForHabitInDate(habit.habitId,date)

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
                    skipped = progress.failed,
                    failed = progress.failed
                )
            }
        }
    }

    override suspend fun getHabit(
        userId: String,
        habitId: String
    ): HabitUi {
        return habitDao.getHabit(userId,habitId)!!.toHabitEntity()  // значение так же может быть Null добавить проверку
    }

//    override suspend fun changeSchedule(
//        habitId: String,
//        schedule: HabitSchedule
//    ) {
//
//    }

    override suspend fun deleteHabit(habitId: String) {
        habitDao.deleteHabit(habitId)
    }

    override suspend fun editHabit(habit: HabitUi) {
        habitDao.addHabit(habit.toDbModel())  // потмо проверить корректность
    }

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
        val habit = HabitUi(
            habitId = UUID.randomUUID().toString(),
            userId = getUserId(),
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
            habitCustom = habitCustom,
        ).toDbModel()
        habitDao.addHabit(habit)
    }

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

    override suspend fun saveProgress(habitProgress: HabitProgress) {
        habitProgressDao.saveProgress(habitProgress.toDbModel())
    }

    override suspend fun getProgressForHabitInDate(
        habitId: String,
        date: LocalDate
    ): HabitProgress {
        return getProgress(habitId,date) ?: addProgressForHabit(habitId, date)
    }

    override suspend fun getProgress(
        habitId: String,
        date: LocalDate
    ): HabitProgress? {
        return habitProgressDao.getProgress(habitId,date)?.toHabitProgressEntity()
    }

    override suspend fun switchCompleteStatus(habitId: String, date: LocalDate) {
        habitProgressDao.switchCompleteStatus(habitId,date)
    }

    override suspend fun switchFailedStatus(habitId: String, date: LocalDate) {
        habitProgressDao.switchFailedStatus(habitId,date)
    }

    override suspend fun switchSkipStatus(habitId: String, date: LocalDate) {
        habitProgressDao.switchSkippedStatus(habitId,date)
    }

    override suspend fun getUserId(): String {
        return userDao.getUserId()!!    // тоже может вернуть Null потом проверка
    }

    override suspend fun getUserCard(userId: String): User {
        return userDao.getCurrentUser()!!.toUserEntity()     // мб потом подправить когда логику поменяется а пока что в базе всего 1 юзер хранится
    }

    override suspend fun login(email: String, password: String): Boolean {
        val hashPassword = hashPassword(password)
        val exist = userDao.authenticate(email,hashPassword)
        return if (exist == null){
            Log.d("Repository","No login! email:$email/ password:$password does not in base incorrect enter data")
            false
        }
        else {
            true
        }
    }

    suspend fun clearLocalDatabase(){
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

    override suspend fun register(registerUser: RegistrationDraft): Boolean {

        clearLocalDatabase()

        val newUserId = UUID.randomUUID().toString()
        val passwordHash = hashPassword(registerUser.password)

        val newUser = registerUser.toDbModelWithoutUserId()
            .copy(
                userId = newUserId,
                password = passwordHash
            )
        userDao.insertUser(newUser)

        registerUser.habbies
            .toHabitDbModelWithoutHabitId(newUserId)
            .forEach { habitWithoutId ->
                val habitWithId = habitWithoutId.copy(
                    habitId = UUID.randomUUID().toString()
                )
                habitDao.addHabit(habitWithId)
            }
        return true // подправить мб проверку организовать тоже
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