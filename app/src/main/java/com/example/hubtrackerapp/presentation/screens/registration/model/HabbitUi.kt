package com.example.hubtrackerapp.presentation.screens.registration.model

data class HabitUi(
    val id: Int,
    val emoji: String,
    val title: String,
    val isSelected: Boolean = false
)