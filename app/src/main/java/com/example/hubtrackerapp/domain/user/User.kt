package com.example.hubtrackerapp.domain.user

import com.example.hubtrackerapp.presentation.screens.registration.model.HabitUi
import java.time.LocalDate

data class User(
    val userId: Int,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val gender: String, // сделать enum класс
    val habbies: List<HabitUi> // сделать enum мб
    )