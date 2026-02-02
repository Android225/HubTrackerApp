package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.HabitDbModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import java.time.LocalDate

fun HabitUi.toDbModel(): HabitDbModel {
    return HabitDbModel(
        habitId = habitId,
        userId = userId,
        emoji = emoji,
        title = title,
        createdAt = createdAt,
        schedule = schedule.toDbModel(),
        isArchived = isArchived,
        color = color,
        target = target,
        metric = metric,
        reminderTime = reminderTime,
        reminderDate = reminderDate.toDbModel(),
        reminderIsActive = reminderIsActive,
        habitType = habitType,
        habitCustom = habitCustom
    )
}

fun HabitDbModel.toHabitEntity(): HabitUi{
    return HabitUi(
        habitId = habitId,
        userId = userId,
        emoji = emoji,
        title = title,
        createdAt = createdAt,
        schedule = schedule.toDomain(),
        isArchived = isArchived,
        color = color,
        target = target,
        metric = metric,
        reminderTime = reminderTime,
        reminderDate = reminderDate.toDomain(),
        reminderIsActive = reminderIsActive,
        habitType = habitType,
        habitCustom = habitCustom
    )
}

fun List<HabitDbModel>.toHabitEntities(): List<HabitUi>{
    return this.map {
        it.toHabitEntity()
    }
}

fun List<HabitUi>.toHabitDbModel(): List<HabitDbModel>{
    return this.map {
        it.toDbModel()
    }
}
fun List<PredefinedHabit>.toHabitDbModelWithoutHabitId(userId: String): List<HabitDbModel>{
    return map { registerHabit ->
        HabitUi(
            habitId = "",
            userId = userId,
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
        ).toDbModel()
    }
}