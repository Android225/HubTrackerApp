package com.example.hubtrackerapp.presentation.screens.registration.model

import com.example.hubtrackerapp.presentation.screens.registration.RegisterHabits

data class RegistrationDraft(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val gender: String = "", // сделать enum класс
    val habbies: List<RegisterHabits> = emptyList()
)