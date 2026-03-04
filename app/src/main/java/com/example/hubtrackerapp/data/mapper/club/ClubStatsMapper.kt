package com.example.hubtrackerapp.data.mapper.club

import com.example.hubtrackerapp.data.db.model.club.ClubStatsDBModel
import com.example.hubtrackerapp.domain.hubbit.models.club.model.ClubStats

fun ClubStats.toClubStatsDbModel(): ClubStatsDBModel {
    return ClubStatsDBModel(
        clubId = clubId,
        totalCompletions = totalCompletions,
        totalChallenges = totalChallenges,
        currentChallengeId = currentChallengeId,
        lastUpdated = lastUpdated
    )
}

fun ClubStatsDBModel.toClubStatsDomain(): ClubStats {
    return ClubStats(
        clubId = clubId,
        totalCompletions = totalCompletions,
        totalChallenges = totalChallenges,
        currentChallengeId = currentChallengeId,
        lastUpdated = lastUpdated
    )
}

fun List<ClubStatsDBModel>.toClubStatsDomainList(): List<ClubStats> = this.map { it.toClubStatsDomain() }
fun List<ClubStats>.toClubStatsDbModelList(): List<ClubStatsDBModel> = this.map { it.toClubStatsDbModel() }