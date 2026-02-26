package com.example.hubtrackerapp.domain.hubbit.models.club

import java.time.LocalDate

data class Club (
    val clubId: String,
    val adminId: String,
    val title: String, // название
    val description: String, // доп инфа клуба
    val imageUrl: String, // пока эмодзи в будующем картинки
    val category: String, // категория клуба типо спорт там,клуб читателей
    val createdAt: Long, //когда сделан
    val isPrivate: Boolean, //приватность клуба (по приглашениям/открытый)
    val lastUpdate: Long //для отслеживания изменений
    )