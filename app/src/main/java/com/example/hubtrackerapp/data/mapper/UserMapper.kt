package com.example.hubtrackerapp.data.mapper

import com.example.hubtrackerapp.data.db.model.UserDbModel
import com.example.hubtrackerapp.domain.user.User

fun User.toDbModel(): UserDbModel {
    return UserDbModel(userId, email, password, firstName, lastName, birthDate, gender)
}
fun UserDbModel.toUserEntity(): User{
    return User(userId,email,password,firstName,lastName,birthDate,gender)
}