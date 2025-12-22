package com.example.hubtrackerapp.domain.hubbit.models

import java.time.LocalDate

data class CalendarDayUi(
    val date: LocalDate,
    val dayNumber: Int,
    val dayOfWeek: String,
    val isToday: Boolean
)