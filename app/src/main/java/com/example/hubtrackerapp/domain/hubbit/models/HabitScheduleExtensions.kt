package com.example.hubtrackerapp.domain.hubbit.models

import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

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

fun HabitSchedule.toDisplayText(): String {
    return when (this) {
        is HabitSchedule.EveryDay -> "Every Day"
        is HabitSchedule.EveryNDays -> "Every $n Days"
        is HabitSchedule.SpecificDays -> {
            if (daysOfWeek.isEmpty()) {
                "Specific Days"
            } else {
                // Сортируем дни недели и форматируем
                val shortDays = daysOfWeek
                    .sortedBy { it.value }
                    .joinToString(", ") { day ->
                        day.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                    }
                "On: $shortDays"
            }
        }
    }
}