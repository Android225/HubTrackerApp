package com.example.hubtrackerapp.domain.hubbit.models.challenges.model

enum class ParticipantStatus {
    INVITED,    // приглашен
    JOINED,     // участвует
    LEFT,       // вышел
    COMPLETED,  // завершил (достиг цели или дошел до конца)
    BANNED      // забанен создателем
}