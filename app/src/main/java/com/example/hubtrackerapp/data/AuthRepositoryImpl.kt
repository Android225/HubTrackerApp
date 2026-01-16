package com.example.hubtrackerapp.data

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.domain.auth.AuthRepository
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.user.User
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
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
                color = Color(0xFF64B5F6),
                target = "8",
                metric = HabitMetric.GLASSES,
                reminderTime = LocalTime.of(9, 0),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "8",
                metric = HabitMetric.KILOMETERS,
                reminderTime = LocalTime.of(9, 0),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "10",
                metric = HabitMetric.KILOMETERS,
                reminderTime = LocalTime.of(9, 0),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "30",
                metric = HabitMetric.MINUTES,
                reminderTime = LocalTime.of(12, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "30",
                metric = HabitMetric.MINUTES,
                reminderTime = LocalTime.of(8, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "60",
                metric = HabitMetric.MINUTES,
                reminderTime = LocalTime.of(10, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "30",
                metric = HabitMetric.MINUTES,
                reminderTime = LocalTime.of(15, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "2",
                metric = HabitMetric.TIMES,
                reminderTime = LocalTime.of(12, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                color = Color(0xFF64B5F6),
                target = "8",
                metric = HabitMetric.HOURS,
                reminderTime = LocalTime.of(23, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitch.BUILD
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
                emoji = registerHabit.icon,
                title = registerHabit.habitName,
                createdAt = LocalDate.now(),
                schedule = registerHabit.habitSchedule,
                color = registerHabit.color,
                target = registerHabit.target,
                metric = registerHabit.metricForHabit,
                reminderTime = registerHabit.reminderTime,
                reminderDate = registerHabit.reminderDate,
                habitType = registerHabit.habitType,
            )
        }

        val exist = usersListFlow.value.any { it.email == user.email }

        if (exist) {
            Log.d("Register","This Account almost exist")
            return false
        }

        usersListFlow.update { oldList ->
            Log.d("Register","User added for base")
            oldList + user
        }
        hubitsStateFlow.update { oldList ->
            Log.d("Register","predefined Habits Added")
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

