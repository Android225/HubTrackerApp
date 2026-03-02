package com.example.hubtrackerapp.domain.hubbit.models.statistic.model

import java.time.LocalDate

data class DetailedStats(
    val period: StatsPeriod,              // Выбранный период (День/Неделя/Месяц/Всё время)
    val totalCompletions: Int,             // Всего выполнений за период
    val totalPoints: Int,                  // Всего очков за период
    val averagePerDay: Float,               // Среднее количество выполнений в день
    val bestDay: LocalDate?,                // Самый продуктивный день
    val bestDayValue: Int,                  // Сколько выполнил в лучший день
    val dailyBreakdown: Map<LocalDate, Int>, // Разбивка по дням (для графика)
    val moodAverage: Float?,                 // Среднее настроение за период
    val moodBreakdown: Map<LocalDate, Int>?  // Настроение по дням (для графика)
)