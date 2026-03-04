package com.example.hubtrackerapp.data.mapper.club

import com.example.hubtrackerapp.data.db.model.club.ClubDBModel
import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club

fun Club.toClubDbModel(): ClubDBModel {
    return ClubDBModel(
        clubId = clubId,
        adminId = adminId,
        title = title,
        description = description,
        imageUrl = imageUrl,
        category = category,
        createdAt = createdAt,
        isPrivate = isPrivate,
        lastUpdate = lastUpdate,
        memberCount = memberCount
    )
}

fun ClubDBModel.toClubDomain(): Club {
    return Club(
        clubId = clubId,
        adminId = adminId,
        title = title,
        description = description,
        imageUrl = imageUrl,
        category = category,
        createdAt = createdAt,
        isPrivate = isPrivate,
        lastUpdate = lastUpdate,
        memberCount = memberCount
    )
}

fun List<ClubDBModel>.toClubDomainList(): List<Club> = this.map { it.toClubDomain() }
fun List<Club>.toClubDbModelList(): List<ClubDBModel> = this.map { it.toClubDbModel() }