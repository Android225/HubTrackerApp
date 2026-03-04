package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.get_actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetUserActionsForDateUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        date: LocalDate
    ): List<UserAction> = repository.getUserActionsForDate(userId, date)
}