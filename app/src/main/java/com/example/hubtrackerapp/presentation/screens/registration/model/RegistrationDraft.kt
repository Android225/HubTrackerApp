package com.example.hubtrackerapp.presentation.screens.registration.model

import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.presentation.screens.registration.RegisterHabits

data class RegistrationDraft(
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val gender: String = "",
    val habbies: List<PredefinedHabit> = emptyList()
)