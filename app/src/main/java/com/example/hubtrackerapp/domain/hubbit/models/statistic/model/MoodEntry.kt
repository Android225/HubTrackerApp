package com.example.hubtrackerapp.domain.hubbit.models.statistic.model

import java.time.LocalDate

data class MoodEntry(
    val date: LocalDate,      // Когда записал настроение
    val mood: Int,            // Оценка настроения (1-5)
    val emoji: String?,       // Какой эмодзи выбрал (😊, 😐, 😢)
    val note: String?         // Текстовый комментарий (почему такое настроение)
)