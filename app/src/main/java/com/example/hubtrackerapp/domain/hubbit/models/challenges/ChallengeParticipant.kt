package com.example.hubtrackerapp.domain.hubbit.models.challenges

data class ChallengeParticipant (
    val challengeId: String,
    val userId: String,

    // Статус
    val status: ParticipantStatus,
    val joinedAt: Long? = null,

    // Прогресс (обновляется при каждом действии)
    val totalValue: Int = 0,  // суммарное значение (шаги, страницы, минуты)
    val completionsCount: Int = 0,  // сколько раз выполнил хобби
    val lastContribution: Long? = null,  // время последнего вклада

    // Для COLLECTIVE_GOAL - личный вклад в общую копилку
    val personalContribution: Int = 0,

    // Для COMPETITIVE - рейтинговые данные
    val rank: Int? = null,
    val isWinner: Boolean = false,

    val updatedAt: Long
)