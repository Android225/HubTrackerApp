package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.mood

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetDailyMoodUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Map<LocalDate, Int> = repository.getDailyMood(userId, fromDate, toDate)
}