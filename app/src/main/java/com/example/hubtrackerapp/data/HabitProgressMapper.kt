package com.example.hubtrackerapp.data

import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.user.User

fun HabitProgress.toDbModel(): HabitProgressDbModel{
    return HabitProgressDbModel(
        habitId = habitId,
        date = date,
        isCompleted = isCompleted,
        progress = progress,
        progressWithTarget = progressWithTarget,
        skipped = skipped,
        failed = failed
    )
}
fun HabitProgressDbModel.toHabitProgressEntity(): HabitProgress{
    return HabitProgress(
        habitId = habitId,
        date = date,
        isCompleted = isCompleted,
        progress = progress,
        progressWithTarget = progressWithTarget,
        skipped = skipped,
        failed = failed
    )
}