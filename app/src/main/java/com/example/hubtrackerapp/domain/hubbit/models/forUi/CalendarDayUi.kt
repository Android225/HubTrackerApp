package com.example.hubtrackerapp.domain.hubbit.models.forUi

import java.time.LocalDate

data class CalendarDayUi(
    val date: LocalDate, // дата для дня месяца
    val dayNumber: Int, // номер дня в месяце
    val dayOfWeek: String, // день недели
    val isToday: Boolean // проверка сегодняшний день в эту дату или нет
)