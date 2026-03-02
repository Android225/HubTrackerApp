package com.example.hubtrackerapp.domain.hubbit.models.statistic.model

import java.time.LocalDate

data class ProfileStats(
    val totalHabitsCompleted: Int,     // Всего выполнено привычек за всё время
    val totalPoints: Int,               // Всего накоплено очков
    val currentStreak: Int,             // Текущий стрик (сколько дней подряд)
    val longestStreak: Int,             // Рекордный стрик
    val averageMood: Float?,            // Среднее настроение (за последние 30 дней)
    val achievementsCount: Int,          // Количество полученных достижений
    val todayCompletions: Int,           // Выполнено сегодня
    val weeklyCompletions: Int,          // Выполнено за последние 7 дней
    val monthlyCompletions: Int,         // Выполнено за последние 30 дней
    val lastActiveDate: LocalDate?       // Когда последний раз что-то делал
)