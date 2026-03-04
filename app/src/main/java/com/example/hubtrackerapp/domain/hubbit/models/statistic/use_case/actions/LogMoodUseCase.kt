package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogMoodUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        mood: Int,
        emoji: String? = null,
        note: String? = null
    ): Result<UserAction> = repository.logMood(mood, emoji, note)
}