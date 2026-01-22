package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress

fun HabitProgress.toDbModel(): HabitProgressDbModel {
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