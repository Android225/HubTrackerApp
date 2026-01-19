package com.example.hubtrackerapp.data

import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

object TempDB {

    var testUser = User(
        userId = UUID.randomUUID().toString(),
        email = "Alifas@hub.com",
        password = "123",
        firstName = "Alifas",
        lastName = "Fazan",
        birthDate = "12/12/2000",
        gender = "Male"
    )
     val testUsers = mutableListOf<User>().apply {
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

     val usersListFlow = MutableStateFlow<List<User>>(testUsers)

     val habitsUsersList = mutableListOf<HabitUi>().apply {
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                habitType = ModeForSwitchInHabit.BUILD
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
                color = Color(0xFF64B5F6),
                target = "8",
                metric = HabitMetric.HOURS,
                reminderTime = LocalTime.of(23, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitchInHabit.BUILD
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
                color = Color(0xFF64B5F6),
                target = "60",
                metric = HabitMetric.MINUTES,
                reminderTime = LocalTime.of(12, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitchInHabit.BUILD
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
                color = Color(0xFF64B5F6),
                target = "30",
                metric = HabitMetric.MINUTES,
                reminderTime = LocalTime.of(12, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitchInHabit.BUILD
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
                color = Color(0xFF64B5F6),
                target = "4",
                metric = HabitMetric.TIMES,
                reminderTime = LocalTime.of(12, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitchInHabit.BUILD
            )
        )
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[8].userId,
                emoji = "🌿",
                title = "Waterasd plant",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryNDays(2),
                color = Color(0xFF64B5F6),
                target = "52",
                metric = HabitMetric.TIMES,
                reminderTime = LocalTime.of(12, 30),
                reminderDate = HabitSchedule.EveryDay,
                habitType = ModeForSwitchInHabit.BUILD
            )
        )
    }

     val habitsStateFlow = MutableStateFlow<List<HabitUi>>(habitsUsersList)

     val progressHabitsList = mutableListOf<HabitProgress>().apply {
        add(
            HabitProgress(
                habitId = habitsStateFlow.value[9].habitId,
                date = LocalDate.now()
            )
        )
        add(
            HabitProgress(
                habitId = habitsStateFlow.value[10].habitId,
                date = LocalDate.now()
            )
        )
        add(
            HabitProgress(
                habitId = habitsStateFlow.value[11].habitId,
                date = LocalDate.now()
            )
        )
        add(
            HabitProgress(
                habitId = habitsStateFlow.value[12].habitId,
                date = LocalDate.now(),
                isCompleted = true
            )
        )
        add(
            HabitProgress(
                habitId = habitsStateFlow.value[13].habitId,
                date = LocalDate.now()
            )
        )
    }
     val progressStateFlow = MutableStateFlow<List<HabitProgress>>(progressHabitsList)
}