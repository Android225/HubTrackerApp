package com.example.hubtrackerapp.data

import com.example.hubtrackerapp.domain.hubbit.HabitRepository
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.hubbit.models.isActive
import com.example.hubtrackerapp.domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

object HabitRepositoryImpl : HabitRepository {


    val testUser = User(
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
        add(
            HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = usersListFlow.value[8].userId,
                emoji = "🌿",
                title = "Waterasd plant",
                createdAt = LocalDate.now(),
                schedule = HabitSchedule.EveryDay,
            )
        )
    }

    private val habitsStateFlow = MutableStateFlow<List<HabitUi>>(habitsUsersList)

    private val progressHabitsList = mutableListOf<HabitProgress>().apply {
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
                date = LocalDate.now()
            )
        )
        add(
            HabitProgress(
                habitId = habitsStateFlow.value[13].habitId,
                date = LocalDate.now()
            )
        )
    }
    private val progressStateFlow = MutableStateFlow<List<HabitProgress>>(progressHabitsList)

    override fun getHabitsWithScheduleForDate(
        userId: String,
        date: LocalDate
    ): Flow<List<HabitWithProgressUi>> {
        val x = habitsStateFlow.map{oldList ->
            oldList.filter {
            it.userId == userId && it.schedule.isActive(createdAt = it.createdAt, date = date)
            }
        }.map {habits ->
            habits.map {habit ->
                val temp = progressStateFlow.value.first { it.habitId == habit.habitId }
                HabitWithProgressUi(
                    habitId = habit.habitId,
                    emoji = habit.emoji,
                    title = habit.title,
                    isCompleted = temp.isCompleted,
                    progress = temp.progress
                )
            }
        }
        return x
    }


    override suspend fun getHabit(userId: String, habitId: String): HabitUi {
        TODO("Not yet implemented")
    }

    override suspend fun changeSchedule(
        habitId: String,
        schedule: HabitSchedule
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteHabit(habitId: String) {
        habitsStateFlow.update { oldList ->
            oldList.map {
                if (it.habitId == habitId){
                    it.copy(isArchived = true)
                } else {
                    it
                }
            }
        }
    }

    override suspend fun editHabit(habit: HabitUi) {
        habitsStateFlow.update { oldList ->
            oldList.map {
                if (it.habitId == habit.habitId && it.userId == habit.userId){
                    habit
                } else {
                    it
                }
            }
        }
    }

    override suspend fun addHabit(
        emoji: String,
        title: String,
        createdAt: LocalDate,
        schedule: HabitSchedule
    ) {
        habitsStateFlow.update { oldList ->
            val habit = HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = testUser.userId, //ИСПРАВВИТЬ!"!!!
                emoji = emoji,
                title = title,
                createdAt = createdAt,
                schedule = schedule,
            )
            oldList + habit
        }
    }



    override suspend fun saveProgress(
        habitProgress: HabitProgress
    ) {
        progressStateFlow.update { oldList ->
            oldList.map {
                if (it.habitId == habitProgress.habitId && it.date == habitProgress.date){
                    habitProgress
                } else {
                    it
                }
            }
        }
    }

    //Взятие прогресс для хобби на какой-то день,
    //если он есть getProgress()
    //если его нет возвращается null
    //следовательно addProgressForHabit() создаем прогресс на текущую дату и возвращаем
    override suspend fun getProgressForHabitInDate(
        habitId: String,
        date: LocalDate
    ): HabitProgress {
       return getProgress(habitId,date) ?: addProgressForHabit(habitId,date)
    }

    override suspend fun addProgressForHabit(
        habitId: String,
        date: LocalDate
    ): HabitProgress {
        val progress = HabitProgress(
            habitId = habitId,
            date = date
        )
        progressStateFlow.update { oldList ->

            oldList + progress
        }
     return progress
    }
    override suspend fun getProgress(
        habitId: String,
        date: LocalDate
    ): HabitProgress? {
        return progressStateFlow.value.firstOrNull{it.habitId == habitId && it.date == date}
    }
}