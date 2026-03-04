package com.example.hubtrackerapp.data.mapper.club

import com.example.hubtrackerapp.data.db.model.club.UserInClubDBModel
import com.example.hubtrackerapp.domain.hubbit.models.club.model.UserInClub

fun UserInClub.toUserInClubDbModel(): UserInClubDBModel {
    return UserInClubDBModel(
        userId = userId,
        clubId = clubId,
        role = role,
        joinedAt = joinedAt
    )
}

fun UserInClubDBModel.toUserInClubDomain(): UserInClub {
    return UserInClub(
        userId = userId,
        clubId = clubId,
        role = role,
        joinedAt = joinedAt
    )
}

fun List<UserInClubDBModel>.toUserInClubDomainList(): List<UserInClub> = this.map { it.toUserInClubDomain() }
fun List<UserInClub>.toUserInClubDbModelList(): List<UserInClubDBModel> = this.map { it.toUserInClubDbModel() }