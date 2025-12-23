package com.example.hubtrackerapp.presentation.screens.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.data.AuthRepositoryImpl
import com.example.hubtrackerapp.domain.auth.RegisterUseCase
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object RegistrationViewModel : ViewModel() {

    private val repository = AuthRepositoryImpl
    private val registerUseCase = RegisterUseCase(repository)

    private val FirstHabbitsOnRegister = mutableListOf<RegisterHabits>().apply {

    }


    private val _state = MutableStateFlow(
        RegistrationHabitsState(
            habits = listOf(
                RegisterHabits(1, "💧", "Drink water"),
                RegisterHabits(2, "🏃‍♀️", "Run"),
                RegisterHabits(3, "📖", "Read books"),
                RegisterHabits(4, "🧘‍♀️", "Meditate"),
                RegisterHabits(5, "🧑‍💻", "Study"),
                RegisterHabits(6, "📕", "Journal"),
                RegisterHabits(7, "🌿", "Water plant"),
                RegisterHabits(8, "😴", "Sleep")
            )
        )
    )

    val state = _state.asStateFlow()

    private var _draft = MutableStateFlow(RegistrationDraft())
    val draft = _draft.asStateFlow()

    fun onHabitClick(id: Int) {
        val chosenHabbits = _state.value.habits.map {
            if (it.id == id) it.copy(isPinned = !it.isPinned) else it
        }
        _state.value = _state.value.copy(habits = chosenHabbits)
        _draft.value = _draft.value.copy(
            habbies = chosenHabbits.filter { it.isPinned }
        )

    }


    /////ВРОДЕ ГАЗ ГАЗ ГАЗ

    fun setEmail(email: String) {
        Log.d("Register", "Set email input - $email")
        Log.d("Register", "draft email 1 - ${_draft.value.email}")
        _draft.value = _draft.value.copy(email = email)
        Log.d("Register", "draft email 1 - ${_draft.value.email}")
    }

    fun setFirtsName(name: String) {
        _draft.value = _draft.value.copy(firstName = name)
    }

    fun setLastName(name: String) {
        _draft.value = _draft.value.copy(lastName = name)
    }

    fun setGender(gender: String) {
        _draft.value = _draft.value.copy(gender = gender)
    }

    fun setDate(date: String) {
        _draft.value = _draft.value.copy(birthDate = date)
    }

    fun setPassword(password: String) {
        _draft.value = _draft.value.copy(password = password)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            Log.d("Register", "On Register Click ${_draft.value}")
            registerUseCase(_draft.value)

        }
    }
}

data class RegisterHabits(
    val id: Int,
    val emoji: String,
    val title: String,
    val isPinned: Boolean = false
)

data class RegistrationHabitsState(
    val habits: List<RegisterHabits> = emptyList()
)