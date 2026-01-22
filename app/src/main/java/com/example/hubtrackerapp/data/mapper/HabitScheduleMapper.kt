package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.HabitScheduleDbModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import java.time.DayOfWeek

fun HabitSchedule.toDbModel(): HabitScheduleDbModel =
    when (this) {
        HabitSchedule.EveryDay -> HabitScheduleDbModel(
            type = "EVERY_DAY"
        )
        is HabitSchedule.EveryNDays -> HabitScheduleDbModel(
            type = "EVERY_N_DAYS",
            n = n
        )
        is HabitSchedule.SpecificDays -> HabitScheduleDbModel(
            type = "SPECIFIC_DAYS",
            days = daysOfWeek.map { it.value } // DayOfWeek → Int
        )
    }
fun HabitScheduleDbModel.toDomain(): HabitSchedule =
    when (type) {
        "EVERY_DAY" -> HabitSchedule.EveryDay

        "EVERY_N_DAYS" -> HabitSchedule.EveryNDays(
            n ?: error("n is null for EVERY_N_DAYS")
        )

        "SPECIFIC_DAYS" -> HabitSchedule.SpecificDays(
            days?.map { DayOfWeek.of(it) }?.toSet() ?: emptySet()
        )

        else -> error("Unknown HabitSchedule type: $type")
    }
