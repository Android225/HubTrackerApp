package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.points

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetPointsEarnedForDateUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        date: LocalDate
    ): Int = repository.getPointsEarnedForDate(userId, date)
}