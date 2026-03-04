package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.streaks

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetStreakHistoryUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        days: Int = 30
    ): List<Pair<LocalDate, Boolean>> = repository.getStreakHistory(userId, days)
}