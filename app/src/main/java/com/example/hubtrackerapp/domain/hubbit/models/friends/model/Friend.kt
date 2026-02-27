package com.example.hubtrackerapp.domain.hubbit.models.friends.model

data class Friend(
    val userId: String,      // чей это друг
    val friendId: String,    // ID друга

    // Дублируем основные данные друга для быстрого отображения
    val friendFirstName: String,
    val friendLastName: String,
    val friendImageUrl: String? = null,

    // Дополнительно
    val points: Int = 0,     // очки друга (можно обновлять)
    val createdAt: Long = System.currentTimeMillis()
)