package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.habits

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetHabitsSkippedCountUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        date: LocalDate
    ): Int = repository.getHabitsSkippedCount(userId, date)
}