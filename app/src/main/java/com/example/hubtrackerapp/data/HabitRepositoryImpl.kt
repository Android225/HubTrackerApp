package com.example.hubtrackerapp.data

import com.example.hubtrackerapp.domain.hubbit.HabitRepository
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.util.UUID

object HabitRepositoryImpl : HabitRepository {


    private val testUser = User(
        userId = UUID.randomUUID().toString(),
        email = "Alifas@hub.com",
        password = "123",
        firstName = "Alifas",
        lastName = "Fazan",
        birthDate = "12/12/2000",
        gender = "Male"
    )

    private val testUsers = mutableListOf<User>().apply {
        repeat(8) {
            add(
                User(
                    userId = UUID.randomUUID().toString(),
                    email = "$it@hub.com",
                    password = "$it",
                    firstName = "$it-FirstName",
                    lastName = "$it-LastName",
                    birthDate = "$it/$it/$it",
                    gender = if (it % 2 == 0) "Male" else "Female"
                )
            )
        }
        add(
            testUser
        )
    }

    private val usersListFlow = MutableStateFlow<List<User>>(testUsers)



    private val habitsUsersList = mutableListOf<HabitUi>().apply {
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[0].userId,
                emoji = "💧",
                title = "Drink water",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[0].userId,
                emoji = "🏃‍♀️",
                title = "Run",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[1].userId,
                emoji = "🏃‍♀️",
                title = "Run",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[2].userId,
                emoji = "📖",
                title = "Read books",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[3].userId,
                emoji = "🧘‍♀️",
                title = "Meditate",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[4].userId,
                emoji = "🧑‍💻",
                title = "Study",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[5].userId,
                emoji = "📕",
                title = "Journal",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[6].userId,
                emoji = "🌿",
                title = "Water plant",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[7].userId,
                emoji = "😴",
                title = "Sleep",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        //// Add Habits To USER

        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[8].userId,
                emoji = "😴",
                title = "Sleep",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[8].userId,
                emoji = "🧑‍💻",
                title = "Study",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[8].userId,
                emoji = "📕",
                title = "Journal",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[8].userId,
                emoji = "🌿",
                title = "Water plant",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
    }

    private val habitsStateFlow = MutableStateFlow<List<HabitUi>>(habitsUsersList)

    override fun getAllHabits(): Flow<List<HabitUi>> {
        return habitsStateFlow
    }

    override suspend fun switchCompleteStatusInThisDate(habitId: String, date: LocalDate) {
        TODO("Not yet implemented")
    }

    override suspend fun getHabit(habitId: String): HabitUi {
        TODO("Not yet implemented")
    }

    override suspend fun changeSchedule(
        habitId: String,
        schedule: HabitSchedule
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHabit(habitId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun editHabit(habit: HabitUi) {
        TODO("Not yet implemented")
    }

    override suspend fun addHabit(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun saveProgress(
        habitId: String,
        date: LocalDate,
        isCompleted: Boolean,
        progress: Float
    ) {
        TODO("Not yet implemented")
    }
}