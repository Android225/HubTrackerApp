package com.example.hubtrackerapp.data.mapper.challenge

import com.example.hubtrackerapp.data.db.model.challenge.ChallengeDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.Challenge
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun Challenge.toChallengeDbModel(): ChallengeDBModel {
    return ChallengeDBModel(
        challengeId = challengeId,
        title = title,
        description = description,
        createdBy = createdBy,
        createdAt = createdAt,
        visibility = visibility,
        clubId = clubId,
        goalType = goalType,
        targetMetric = targetMetric,
        targetValue = targetValue,
        startDate = startDate.toEpochDayLong(),
        endDate = endDate?.toEpochDayLong(),
        isActive = isActive,
        maxParticipants = maxParticipants,
        currentParticipants = currentParticipants
    )
}

fun ChallengeDBModel.toChallengeDomain(): Challenge {
    return Challenge(
        challengeId = challengeId,
        title = title,
        description = description,
        createdBy = createdBy,
        createdAt = createdAt,
        visibility = visibility,
        clubId = clubId,
        goalType = goalType,
        targetMetric = targetMetric,
        targetValue = targetValue,
        startDate = startDate.toLocalDateFromEpochDay(),
        endDate = endDate?.toLocalDateFromEpochDay(),
        isActive = isActive,
        maxParticipants = maxParticipants,
        currentParticipants = currentParticipants
    )
}

fun List<ChallengeDBModel>.toChallengeDomainList(): List<Challenge> = this.map { it.toChallengeDomain() }
fun List<Challenge>.toChallengeDbModelList(): List<ChallengeDBModel> = this.map { it.toChallengeDbModel() }

// Вспомогательные функции для конвертации дат
fun LocalDate.toEpochDayLong(): Long = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
fun Long.toLocalDateFromEpochDay(): LocalDate = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()