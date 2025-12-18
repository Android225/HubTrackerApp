package com.example.hubtrackerapp.presentation.screens.registration.model

data class RegistrationDraft(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val gender: String = "", // сделать enum класс
    val habbies: List<HabitUi> = emptyList()
)