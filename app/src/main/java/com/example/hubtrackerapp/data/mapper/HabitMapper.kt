package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.HabitDbModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi

fun HabitUi.toDbModel(): HabitDbModel {
    return HabitDbModel(
        habitId = habitId,
        userId = userId,
        emoji = emoji,
        title = title,
        createdAt = createdAt,
        schedule = schedule,
        isArchived = isArchived,
        color = color,
        target = target,
        metric = metric,
        reminderTime = reminderTime,
        reminderDate = reminderDate,
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
        schedule = schedule,
        isArchived = isArchived,
        color = color,
        target = target,
        metric = metric,
        reminderTime = reminderTime,
        reminderDate = reminderDate,
        reminderIsActive = reminderIsActive,
        habitType = habitType,
        habitCustom = habitCustom
    )
}