package com.example.hubtrackerapp.presentation.screens.registration

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegistrationViewModel(): ViewModel() {

    private val _state = MutableStateFlow(
        RegistrationHabitsState(
            habits = listOf(
                HabitUi(1, "💧", "Drink water"),
                HabitUi(2, "🏃‍♀️", "Run"),
                HabitUi(3, "📖", "Read books"),
                HabitUi(4, "🧘‍♀️", "Meditate"),
                HabitUi(5, "🧑‍💻", "Study"),
                HabitUi(6, "📕", "Journal"),
                HabitUi(7, "🌿", "Water plant"),
                HabitUi(8, "😴", "Sleep"),
            )
        )
    )

    val state = _state.asStateFlow()

    fun onHabitClick(id: Int) {
        _state.value = _state.value.copy(
            habits = _state.value.habits.map {
                if (it.id == id)
                    it.copy(isSelected = !it.isSelected)
                else it
            }
        )
    }
}

data class HabitUi(
    val id: Int,
    val emoji: String,
    val title: String,
    val isSelected: Boolean = false
)

data class RegistrationHabitsState(
    val habits: List<HabitUi> = emptyList()
)