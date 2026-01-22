package com.example.hubtrackerapp.presentation.screens.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.hubtrackerapp.data.AuthRepositoryImpl
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.data.predefined.PredefinedHabitRepositoryImpl
import com.example.hubtrackerapp.domain.auth.RegisterUseCase
import com.example.hubtrackerapp.domain.hubbit.AddHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.GetAllPredefinedHabitsUseCase
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerUseCase:RegisterUseCase,
    private val getAllPredefinedHabits:GetAllPredefinedHabitsUseCase,
   // val predefinedHabits: List<PredefinedHabit>
) : ViewModel() {

//    private val repository = AuthRepositoryImpl
//    private val predefinedRepository = PredefinedHabitRepositoryImpl
//    private val registerUseCase = RegisterUseCase(repository)
//    private val getAllPredefinedHabits = GetAllPredefinedHabitsUseCase(predefinedRepository)

    private val _state = MutableStateFlow(RegistrationState.Creation())
    val state = _state.asStateFlow()

    private var predefinedHabits = emptyList<PredefinedHabit>()

    fun onEventRegister(event: RegisterEvent){
        when(event){
            is RegisterEvent.ChoseGender -> {

                Log.d("Register", "ChoseGender On Register Click ${_state.value.registrationDraft}")

                _state.update {
                    Log.d("Register", "Set gender input - ${event.gender}")
                    it.copy(registrationDraft = it.registrationDraft.copy(gender = event.gender))
                }
            }
            is RegisterEvent.ChosePredefinedHabits -> {


                Log.d("Register", "ChosePredefinedHabits On Register Click ${_state.value.registrationDraft}")

                //при клике меняется состояние у списка хобби
                val updatedHabits = _state.value.habits.map {
                    if (it.id == event.predefinedHabitId) it.copy(isPinned = !it.isPinned) else it
                }

                val selectedHabits: List<PredefinedHabit> = updatedHabits
                    .filter { it.isPinned }
                    .sortedBy { it.id }
                    .mapNotNull { registerHabits ->
                        predefinedHabits.getOrNull(registerHabits.id)
                    }
                Log.d("Register", "Set Add Habits in list input - ${event.predefinedHabitId}")


                _state.update {it.copy(habits = updatedHabits)}

                _state.update {it.copy(predefinedHabits = selectedHabits)}
//
            }
            RegisterEvent.RegisterUser -> {

                Log.d("Register", "RegisterUser On Register Click ${_state.value.registrationDraft}")

                viewModelScope.launch {
                    Log.d("Register", "On Register Click ${_state.value.registrationDraft}")

                    val updatedDraft = _state.value.registrationDraft.copy(
                        habbies = _state.value.predefinedHabits // ← ВОТ ОНО! Передаем выбранные привычки
                    )
                    Log.d("Register", "Draft with hobbies: $updatedDraft")
                    val success = registerUseCase(updatedDraft)

                    if (success) {
                        Log.d("Register", "Registration successful")
                        // Можно перейти на другой экран
                        // _state.update { RegistrationState.Finished }
                    } else {
                        Log.d("Register", "Registration failed")
                        // Можно показать ошибку
                    }

                }
            }
            is RegisterEvent.SetBirthDate -> {



                _state.update {
                    Log.d("Register", "Set birthDate input - ${event.birthDate}")
                    it.copy(registrationDraft = it.registrationDraft.copy(birthDate = event.birthDate))
                }
            }
            is RegisterEvent.SetEmail -> {
                _state.update {
                    Log.d("Register", "Set email input - ${event.email}")
                    it.copy(registrationDraft = it.registrationDraft.copy(email = event.email))
                }
            }
            is RegisterEvent.SetFirstName -> {
                _state.update {
                    Log.d("Register", "Set firstName input - ${event.firstName}")
                    it.copy(registrationDraft = it.registrationDraft.copy(firstName = event.firstName))
                }
            }
            is RegisterEvent.SetPassword -> {
                _state.update {
                    Log.d("Register", "Set password input - ${event.password}")
                    it.copy(registrationDraft = it.registrationDraft.copy(password = event.password))
                }
            }
            is RegisterEvent.SetSecondName -> {
                _state.update {
                    Log.d("Register", "Set secondName input - ${event.secondName}")
                    it.copy(registrationDraft = it.registrationDraft.copy(lastName = event.secondName))
                }
            }
        }
    }

    init {
        Log.d("RegistrationViewModel", "ViewModel CREATED - ${this.hashCode()}")
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
}

data class RegisterHabits(
    val id: Int,
    val emoji: String,
    val title: String,
    val isPinned: Boolean = false
)

sealed interface RegistrationState{
    data object Finished: RegistrationState

    data class Creation(
        val registrationDraft: RegistrationDraft = RegistrationDraft(),
        val habits: List<RegisterHabits> = emptyList(),
        val predefinedHabits: List<PredefinedHabit> = emptyList()
    ): RegistrationState {
        val isRegisterEnabled: Boolean
            get() {
                return when {
                    registrationDraft.email.isBlank() -> false
                    registrationDraft.password.isBlank() -> false
                    else -> {
                        Log.d("Register", "Is Register Enabled")
                        true
                    }
                }
            }
    }
}




sealed interface RegisterEvent {

    data object RegisterUser: RegisterEvent
    data class SetFirstName(val firstName: String): RegisterEvent
    data class SetSecondName(val secondName: String): RegisterEvent
    data class SetBirthDate(val birthDate:String): RegisterEvent
    data class ChoseGender(val gender: String): RegisterEvent

    data class ChosePredefinedHabits(val predefinedHabitId: Int): RegisterEvent
    data class SetEmail(val email: String): RegisterEvent
    data class SetPassword(val password: String): RegisterEvent
}