package com.example.hubtrackerapp.domain.hubbit.models.challenges

enum class ParticipantStatus {
    INVITED,    // приглашен
    JOINED,     // участвует
    LEFT,       // вышел
    COMPLETED,  // завершил (достиг цели или дошел до конца)
    BANNED      // забанен создателем
}