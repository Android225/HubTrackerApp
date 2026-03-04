package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.actions

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.UserAction
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import javax.inject.Inject

class LogClubJoinedUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        clubId: String,
        clubName: String
    ): Result<UserAction> = repository.logClubJoined(clubId, clubName)
}