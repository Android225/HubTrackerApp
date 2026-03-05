package com.example.hubtrackerapp.data.mapper.challenge

import com.example.hubtrackerapp.data.db.model.challenge.ChallengeHabitDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeHabit

fun ChallengeHabit.toChallengeHabitDbModel(): ChallengeHabitDBModel {
    return ChallengeHabitDBModel(
        challengeId = challengeId,
        habitId = habitId,
        habitName = habitName,
        habitIcon = habitIcon,
        habitColor = habitColor,
        metric = metric,
        target = target,
        scheduleType = scheduleType,
        scheduleValue = scheduleValue,
        activeFromDate = activeFromDate,
        activeToDate = activeToDate,
        createdAt = createdAt
    )
}

fun ChallengeHabitDBModel.toChallengeHabitDomain(): ChallengeHabit {
    return ChallengeHabit(
        challengeId = challengeId,
        habitId = habitId,
        habitName = habitName,
        habitIcon = habitIcon,
        habitColor = habitColor,
        metric = metric,
        target = target,
        scheduleType = scheduleType,
        scheduleValue = scheduleValue,
        activeFromDate = activeFromDate,
        activeToDate = activeToDate,
        createdAt = createdAt
    )
}

fun List<ChallengeHabitDBModel>.toChallengeHabitDomainList(): List<ChallengeHabit> =
    this.map { it.toChallengeHabitDomain() }
fun List<ChallengeHabit>.toChallengeHabitDbModelList(): List<ChallengeHabitDBModel> =
    this.map { it.toChallengeHabitDbModel() }