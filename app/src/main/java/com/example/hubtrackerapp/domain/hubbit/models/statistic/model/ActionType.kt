package com.example.hubtrackerapp.domain.hubbit.models.statistic.model

enum class ActionType {
    HABIT_COMPLETED,     // выполнил хобби
    HABIT_SKIPPED,       // пропустил хобби
    HABIT_FAILED,        // провалил
    CHALLENGE_STARTED,   // начал челлендж
    CHALLENGE_COMPLETED, // завершил челлендж
    CHALLENGE_FAILED,    // провалил челлендж
    ACHIEVEMENT_EARNED,  // получил ачивку
    CLUB_JOINED,         // вступил в клуб
    CLUB_LEFT,           // покинул клуб
    MOOD_LOGGED          // записал настроение
}