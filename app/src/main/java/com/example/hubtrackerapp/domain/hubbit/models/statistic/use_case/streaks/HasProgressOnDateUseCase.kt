package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.streaks

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class HasProgressOnDateUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        date: LocalDate
    ): Boolean = repository.hasProgressOnDate(userId, date)
}