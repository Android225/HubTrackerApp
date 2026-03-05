package com.example.hubtrackerapp.data.mapper.challenge

import com.example.hubtrackerapp.data.db.model.challenge.UserCurrentChallengeDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.UserCurrentChallenge

fun UserCurrentChallenge.toUserCurrentChallengeDbModel(): UserCurrentChallengeDBModel {
    return UserCurrentChallengeDBModel(
        userId = userId,
        challengeId = challengeId,
        challengeTitle = challengeTitle,
        challengeIcon = challengeIcon,
        challengeColor = challengeColor,
        startDate = startDate.toEpochDayLong(),
        endDate = endDate?.toEpochDayLong(),
        goalType = goalType,
        targetMetric = targetMetric,
        targetValue = targetValue,
        myProgress = myProgress,
        myCompletions = myCompletions,
        joinedAt = joinedAt
    )
}

fun UserCurrentChallengeDBModel.toUserCurrentChallengeDomain(): UserCurrentChallenge {
    return UserCurrentChallenge(
        userId = userId,
        challengeId = challengeId,
        challengeTitle = challengeTitle,
        challengeIcon = challengeIcon,
        challengeColor = challengeColor,
        startDate = startDate.toLocalDateFromEpochDay(),
        endDate = endDate?.toLocalDateFromEpochDay(),
        goalType = goalType,
        targetMetric = targetMetric,
        targetValue = targetValue,
        myProgress = myProgress,
        myCompletions = myCompletions,
        joinedAt = joinedAt
    )
}

fun List<UserCurrentChallengeDBModel>.toUserCurrentChallengeDomainList(): List<UserCurrentChallenge> =
    this.map { it.toUserCurrentChallengeDomain() }
fun List<UserCurrentChallenge>.toUserCurrentChallengeDbModelList(): List<UserCurrentChallengeDBModel> =
    this.map { it.toUserCurrentChallengeDbModel() }