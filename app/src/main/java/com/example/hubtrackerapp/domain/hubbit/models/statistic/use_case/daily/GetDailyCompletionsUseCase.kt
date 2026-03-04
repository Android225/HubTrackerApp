package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.daily

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDailyCompletionsUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int> = repository.getDailyCompletions(userId, fromDate, toDate)
}