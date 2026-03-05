package com.example.hubtrackerapp.data.mapper.challenge

import com.example.hubtrackerapp.data.db.model.challenge.ChallengeParticipantDBModel
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeParticipant

fun ChallengeParticipant.toChallengeParticipantDbModel(): ChallengeParticipantDBModel {
    return ChallengeParticipantDBModel(
        challengeId = challengeId,
        userId = userId,
        status = status,
        joinedAt = joinedAt,
        totalValue = totalValue,
        completionsCount = completionsCount,
        lastContribution = lastContribution,
        personalContribution = personalContribution,
        rank = rank,
        isWinner = isWinner,
        updatedAt = updatedAt
    )
}

fun ChallengeParticipantDBModel.toChallengeParticipantDomain(): ChallengeParticipant {
    return ChallengeParticipant(
        challengeId = challengeId,
        userId = userId,
        status = status,
        joinedAt = joinedAt,
        totalValue = totalValue,
        completionsCount = completionsCount,
        lastContribution = lastContribution,
        personalContribution = personalContribution,
        rank = rank,
        isWinner = isWinner,
        updatedAt = updatedAt
    )
}

fun List<ChallengeParticipantDBModel>.toChallengeParticipantDomainList(): List<ChallengeParticipant> =
    this.map { it.toChallengeParticipantDomain() }
fun List<ChallengeParticipant>.toChallengeParticipantDbModelList(): List<ChallengeParticipantDBModel> =
    this.map { it.toChallengeParticipantDbModel() }