package com.example.hubtrackerapp.data

import android.util.Log
import com.example.hubtrackerapp.domain.auth.AuthRepository
import com.example.hubtrackerapp.domain.user.HabitSchedule
import com.example.hubtrackerapp.domain.user.User
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationHabitsState
import com.example.hubtrackerapp.domain.user.HabitUi
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

object AuthRepositoryImpl : AuthRepository {

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
    }

    private val usersListFlow = MutableStateFlow<List<User>>(testUsers)


    private val hubitsUsersList = mutableListOf<HabitUi>().apply {
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
    }
    private val hubitsStateFlow = MutableStateFlow<List<HabitUi>>(hubitsUsersList)




    override fun login(email: String, password: String): Boolean {
        return email == "Admin@Admin" && password == "Admin"
    }

    override fun register(
        registerUser: RegistrationDraft
    ): Boolean {

        val user = User(
            userId = UUID.randomUUID().toString(),
            email = registerUser.email,
            password = registerUser.password,
            firstName = registerUser.firstName,
            lastName = registerUser.lastName,
            birthDate = registerUser.birthDate,
            gender = registerUser.gender
        )

        val habbitsList = registerUser.habbies.map { registerHabit ->
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = user.userId,
                emoji = registerHabit.emoji,
                title = registerHabit.title,
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay
            )
        }

        val exist = usersListFlow.value.any { it.email == user.email }

        if (exist) {
            return false
        }

        usersListFlow.update { oldList ->

            oldList + user
        }
        hubitsStateFlow.update { oldList ->
            oldList + habbitsList
        }
        Log.d(
            "Register",
            "REPOSITORE ${usersListFlow.value.last().email} - ${usersListFlow.value.last().email}"
        )
        Log.d(
            "Register",
            "REPOSITOREDataToADd ${user} - ${habbitsList}"
        )
        return true
    }

}

