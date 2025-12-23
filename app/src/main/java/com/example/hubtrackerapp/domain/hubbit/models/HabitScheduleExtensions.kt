package com.example.hubtrackerapp.domain.hubbit.models

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun HabitSchedule.isActive(
    createdAt: LocalDate,
    date: LocalDate
): Boolean{
    if (date.isBefore(createdAt)) return false

    return when(this) {
        HabitSchedule.EveryDay -> true
        is HabitSchedule.EveryNDays -> {
            val daysBetween = ChronoUnit.DAYS.between(createdAt,date)
            (daysBetween % n) == 0L
        }
        is HabitSchedule.SpecificDays -> {
            date.dayOfWeek in daysOfWeek
        }
    }
}