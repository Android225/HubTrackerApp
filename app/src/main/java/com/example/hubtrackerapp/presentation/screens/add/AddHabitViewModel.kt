package com.example.hubtrackerapp.presentation.screens.add

import HabitMetric
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.hubtrackerapp.data.HabitRepositoryImpl
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.domain.hubbit.AddHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class AddHabitViewModel : ViewModel() {

    private val repository = HabitRepositoryImpl
    private val addHabitUseCase = AddHabitUseCase(repository)
    val predifinedHabits = PredefinedHabitData.habits

    private val _state = MutableStateFlow(predifinedHabits.first())
    val state = _state.asStateFlow()

    private val _addHabitUiState = MutableStateFlow(AddHabitUiState())
    val addHabitUiState = _addHabitUiState.asStateFlow()
    fun setHabitName(habitName: String) {
        _state.value = _state.value.copy(habitName = habitName)
    }

    fun changeMode(newMode: ModeForSwitch) {
        _state.value = _state.value.copy(habitType = newMode)
    }

    fun onEventAddHabit(event: AddHabitEvent) {
        when (event) {

            is AddHabitEvent.NameChanged -> {
                _state.update { it.copy(habitName = event.habitName) }
            }

            is AddHabitEvent.SelectColor -> {
                _state.update { it.copy(color = event.color) }
                _addHabitUiState.update { it.copy(activePicker = PickerType.Close) }
            }

            is AddHabitEvent.SelectHabitSchedule -> {
                _state.update { it.copy(habitSchedule = event.habitSchedule) }

            }

            is AddHabitEvent.SelectIcon -> {
                val newIcon = event.icon
                    .trim()
                    .let { text ->
                        if (text.isEmpty()) ""
                        else text.codePoints().limit(1).toArray()
                            .let { String(it, 0, it.size) }
                    }
                _state.update { it.copy(icon = newIcon, habitCustom = true) }
            }

            is AddHabitEvent.SelectMetric -> {
                val filteredTarget = event.target
                    .filter { it.isDigit() }          // оставляем только цифры
                    .take(3)                          // не больше 3 символов
                    .trimStart('0')                   // убираем ведущие нули
                    _state.update { it.copy(metricForHabit = event.metric, target = filteredTarget) }

            }

            is AddHabitEvent.SelectTimeAndDate -> {
                _state.update {
                    it.copy(
                        reminderTime = event.reminderTime,
                        reminderDate = event.reminderDate
                    )
                }
                _addHabitUiState.update { it.copy(activePicker = PickerType.Close) }
            }

            is AddHabitEvent.ApplyPredefinedHabit -> {
                _state.value = event.habit
            }

            is AddHabitEvent.SelectHabitType -> {
                _state.update { it.copy(habitType = event.habitType) }
            }

            AddHabitEvent.SwitchReminder -> {
                _state.update { it.copy(reminderIsActive = !it.reminderIsActive) }
            }

            is AddHabitEvent.OpenPicker -> {
                _addHabitUiState.update { it.copy(activePicker = event.pickerType) }
            }

            AddHabitEvent.ClosePicker -> {
                _addHabitUiState.update {
                    it.copy(activePicker = PickerType.Close)
                }
            }
        }

    }
}

    sealed class PickerType {
        object Close : PickerType()
        object Icon : PickerType()
        object Color : PickerType()
        object Goal : PickerType()
        object Schedule : PickerType()
        object Reminder : PickerType()
    }

    data class AddHabitUiState(
        val activePicker: PickerType = PickerType.Close
    )

    sealed interface AddHabitEvent {

        object SwitchReminder : AddHabitEvent
        object ClosePicker : AddHabitEvent

        data class OpenPicker(val pickerType: PickerType) : AddHabitEvent
        data class NameChanged(val habitName: String) : AddHabitEvent
        data class SelectIcon(val icon: String) : AddHabitEvent
        data class SelectColor(val color: Color) : AddHabitEvent

        data class ApplyPredefinedHabit(val habit: PredefinedHabit) : AddHabitEvent

        data class SelectMetric(val metric: HabitMetric, val target: String) : AddHabitEvent
        data class SelectHabitSchedule(val habitSchedule: HabitSchedule) : AddHabitEvent
        data class SelectTimeAndDate(val reminderTime: LocalTime, val reminderDate: HabitSchedule) : AddHabitEvent

        data class SelectHabitType(val habitType: ModeForSwitch) : AddHabitEvent
    }