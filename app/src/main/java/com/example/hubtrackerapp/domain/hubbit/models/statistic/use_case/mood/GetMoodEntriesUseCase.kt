package com.example.hubtrackerapp.domain.hubbit.models.statistic.use_case.mood

import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.MoodEntry
import com.example.hubtrackerapp.domain.hubbit.models.statistic.repository.StatisticRepository
import java.time.LocalDate
import javax.inject.Inject

class GetMoodEntriesUseCase @Inject constructor(
    private val repository: StatisticRepository
) {
    suspend operator fun invoke(
        userId: String,
        fromDate: LocalDate,
        toDate: LocalDate
    ): List<MoodEntry> = repository.getMoodEntries(userId, fromDate, toDate)
}