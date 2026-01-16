package com.example.hubtrackerapp.presentation.screens.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.data.AuthRepositoryImpl
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.data.predefined.PredefinedHabitRepositoryImpl
import com.example.hubtrackerapp.domain.auth.RegisterUseCase
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.GetAllPredefinedHabitsUseCase
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val repository = AuthRepositoryImpl
    private val predifinedRepository = PredefinedHabitRepositoryImpl
    private val registerUseCase = RegisterUseCase(repository)
    private val getAllPredefinedHabits = GetAllPredefinedHabitsUseCase(predifinedRepository)
    private val _state = MutableStateFlow(
        RegistrationHabitsState()
    )
    val state = _state.asStateFlow()

    private var _draft = MutableStateFlow(RegistrationDraft())
    val draft = _draft.asStateFlow()
    private var predefinedHabits = emptyList<PredefinedHabit>()
    init {
        viewModelScope.launch {
            loadPredefinedHabits()
        }
    }
    private suspend fun loadPredefinedHabits(){
        predefinedHabits = getAllPredefinedHabits()
        val habits = predefinedHabits.mapIndexed {index, habit ->
            RegisterHabits(index,habit.icon,habit.habitName)
        }
        _state.update {
            it.copy(habits = habits)
        }
    }
    //временная переменная для заполнения



    /////ВРОДЕ ГАЗ ГАЗ ГАЗ

    fun setEmail(email: String) {
        Log.d("Register", "Set email input - $email")
        Log.d("Register", "draft email 1 - ${_draft.value.email}")
        _draft.value = _draft.value.copy(email = email)
        Log.d("Register", "draft email 1 - ${_draft.value.email}")
    }

    fun setFirstName(name: String) {
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
    fun onHabitClick(id: Int) {
        val updatedHabits = _state.value.habits.map {
            if (it.id == id) it.copy(isPinned = !it.isPinned) else it
        }

        val selectedHabits: List<PredefinedHabit> = updatedHabits
            .filter { it.isPinned }
            .sortedBy { it.id }
            .mapNotNull { registerHabits ->
                predefinedHabits.getOrNull(registerHabits.id)
            }
        _state.update {it.copy(habits = updatedHabits)}
        _draft.update {
            it.copy(
                habbies = selectedHabits
            )
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