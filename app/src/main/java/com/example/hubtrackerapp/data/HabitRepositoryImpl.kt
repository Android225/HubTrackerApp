package com.example.hubtrackerapp.data

import HabitMetric
import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.data.TempDB.habitsStateFlow
import com.example.hubtrackerapp.data.TempDB.progressStateFlow
import com.example.hubtrackerapp.data.TempDB.testUser
import com.example.hubtrackerapp.domain.hubbit.HabitRepository
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.hubbit.models.isActive
import com.example.hubtrackerapp.domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

object HabitRepositoryImpl : HabitRepository {


    override fun getHabitsWithScheduleForDate(
        userId: String,
        date: LocalDate
    ): Flow<List<HabitWithProgressUi>> {

        return combine(
            habitsStateFlow,
            progressStateFlow
        ){ habits, progresses ->

            habits.filter { it.userId == userId &&
                    it.schedule.isActive(createdAt = it.createdAt, date = date)
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

//    override fun getHabitsWithScheduleForDate(
//        userId: String,
//        date: LocalDate
//    ): Flow<List<HabitWithProgressUi>> {
//        val x = habitsStateFlow.map{oldList ->
//            oldList.filter {
//                it.userId == userId && it.schedule.isActive(createdAt = it.createdAt, date = date)
//            }
//        }.map {habits ->
//            habits.map {habit ->
//                val temp = progressStateFlow.value.first { it.habitId == habit.habitId }
//                HabitWithProgressUi(
//                    habitId = habit.habitId,
//                    emoji = habit.emoji,
//                    title = habit.title,
//                    isCompleted = temp.isCompleted,
//                    progress = temp.progress
//                )
//            }
//        }
//        return x
//    }


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

        habitsStateFlow.update { oldList ->
            val habit = HabitUi(
                habitId = UUID.randomUUID().toString(),
                userId = testUser.userId, //ИСПРАВВИТЬ!"!!!
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
            )
            Log.d("Habit repository","Add habit -> $habit")
            oldList + habit
        }
    }



    //скорей всего удалить не нужен будет.!!!!!!
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
            date = date,

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

    override suspend fun switchCompleteStatus(habitId: String, date: LocalDate) {
        progressStateFlow.update { oldList->
            oldList.map {
                if (it.habitId == habitId && it.date == date){
                    it.copy(isCompleted = !it.isCompleted)
                }else {
                    it
                }
            }
        }
    }

    override suspend fun getUserId(): String {
        return testUser.userId
    }

    override suspend fun getUserCard(userId: String): User {
        return User(
            userId = userId,
            email = testUser.email,
            firstName = testUser.firstName,
            lastName = testUser.lastName,
            birthDate = testUser.birthDate,
            gender = testUser.gender
        )
    }
}