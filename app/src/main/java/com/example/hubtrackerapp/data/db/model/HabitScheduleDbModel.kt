package com.example.hubtrackerapp.data.db.model

data class HabitScheduleDbModel(
    val type: String,            // EVERY_DAY, EVERY_N_DAYS, SPECIFIC_DAYS
    val n: Int? = null,          // только для EveryNDays
    val days: List<Int>? = null  // DayOfWeek.value (1..7)
)