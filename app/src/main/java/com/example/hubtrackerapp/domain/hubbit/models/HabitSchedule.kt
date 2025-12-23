package com.example.hubtrackerapp.domain.hubbit.models

import java.time.DayOfWeek

sealed class HabitSchedule {
    object EveryDay : HabitSchedule()
    data class EveryNDays(val n: Int) : HabitSchedule()
    data class SpecificDays(val daysOfWeek: Set<DayOfWeek>): HabitSchedule()
}