package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.mood

import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetAverageMoodUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): Float? = repository.getAverageMood(userId, fromDate, toDate)
}