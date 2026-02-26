package com.example.hubtrackerapp.domain.hubbit.models.club

data class UserInClub (
    val userId: String,
    val clubId: String,

    val role: RoleMode, //
    val joinedAt: Long
)