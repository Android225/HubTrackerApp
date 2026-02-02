package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.UserDbModel
import com.example.hubtrackerapp.domain.user.User
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft

fun User.toDbModel(): UserDbModel {
    return UserDbModel(userId, email, password, firstName, lastName, birthDate, gender)
}
fun UserDbModel.toUserEntity(): User{
    return User(userId,email,password,firstName,lastName,birthDate,gender)
}
fun RegistrationDraft.toDbModelWithoutUserId(): UserDbModel{
    return UserDbModel(
        userId = "",
        email = email,
        password = password,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
        gender = gender
    )
}