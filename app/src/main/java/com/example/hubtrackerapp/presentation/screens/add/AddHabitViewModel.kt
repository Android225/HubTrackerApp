package com.example.hubtrackerapp.presentation.screens.add

import HabitMetric
import androidx.compose.runtime.isTraceInProgress
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.hubtrackerapp.data.PredefinedHabitData
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime

class AddHabitViewModel : ViewModel() {
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

    fun onEventAddHabit(event: AddHabitEvent){
        when(event) {
            AddHabitEvent.OpenColorPicker -> {
                _addHabitUiState.update { it.copy(isColorPickerVisible = true) }
            }
            AddHabitEvent.OpenGoalEditPicker -> {
                _addHabitUiState.update { it.copy(isGoalEditPickerVisible = true) }
            }
            AddHabitEvent.OpenIconPicker -> {
                _addHabitUiState.update { it.copy(isIconPickerVisible = true) }
            }
            AddHabitEvent.OpenSchedulePicker -> {
                _addHabitUiState.update { it.copy(isSchedulePickerVisible = true) }
            }
            AddHabitEvent.ReminderTimeAndDatePicker -> {
                _addHabitUiState.update { it.copy(isReminderTimeAndDatePickerVisible = true) }
            }
            AddHabitEvent.ClosePickers -> {
                _addHabitUiState.update { it.copy(
                    isColorPickerVisible = false,
                    isIconPickerVisible = false,
                    isGoalEditPickerVisible = false,
                    isSchedulePickerVisible = false,
                    isReminderTimeAndDatePickerVisible = false
                ) }
            }
            is AddHabitEvent.NameChanged -> {
                _state.update { it.copy(habitName = event.habitName) }
            }
            is AddHabitEvent.SelectColor -> {
                _state.update { it.copy(color = event.color) }
                _addHabitUiState.update { it.copy(isColorPickerVisible = false) }
            }
            is AddHabitEvent.SelectHabitSchedule -> {
                _state.update { it.copy(habitSchedule = event.habitSchedule) }
                _addHabitUiState.update { it.copy(isSchedulePickerVisible = false) }
            }
            is AddHabitEvent.SelectIcon -> {
                _state.update { it.copy(icon = event.icon, habitCustom = true) }
                _addHabitUiState.update { it.copy(isIconPickerVisible = false) }
            }
            is AddHabitEvent.SelectMetric -> {
                _state.update { it.copy(metricForHabit = event.metric, target = event.target) }
                _addHabitUiState.update { it.copy(isGoalEditPickerVisible = false) }
            }
            is AddHabitEvent.SelectTimeAndDate -> {
                _state.update { it.copy(reminderTime = event.reminderTime, reminderDate = event.reminderDate) }
                _addHabitUiState.update { it.copy(isReminderTimeAndDatePickerVisible = false) }
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


        }
    }

}

data class AddHabitUiState(
    val isColorPickerVisible: Boolean = false, // всплывающий списко выбора цвета
    val isIconPickerVisible: Boolean = false, // всплывающий список выбора иконок и готовых пресетов хобби
    val isGoalEditPickerVisible: Boolean = false, // всплывающий список выбора метрики и цели которой надо достигать в данном хобби
    val isSchedulePickerVisible: Boolean = false, // всплывающий список выбор в какие дни показывать
    val isReminderTimeAndDatePickerVisible: Boolean = false // всплывающий список выбора даты и времени уведомлений
)

sealed interface AddHabitEvent {
    object OpenIconPicker : AddHabitEvent
    object OpenColorPicker : AddHabitEvent
    object ClosePickers : AddHabitEvent
    object OpenGoalEditPicker : AddHabitEvent
    object OpenSchedulePicker : AddHabitEvent
    object ReminderTimeAndDatePicker : AddHabitEvent
    object SwitchReminder : AddHabitEvent


    data class NameChanged(val habitName: String) : AddHabitEvent
    data class SelectIcon(val icon: String) : AddHabitEvent
    data class SelectColor(val color: Color) : AddHabitEvent

    data class ApplyPredefinedHabit(val habit: PredefinedHabit) : AddHabitEvent

    data class SelectMetric(val metric: HabitMetric,val target: Int) : AddHabitEvent
    data class SelectHabitSchedule(val habitSchedule: HabitSchedule) : AddHabitEvent
    data class SelectTimeAndDate(val reminderTime: LocalTime, val reminderDate: HabitSchedule) : AddHabitEvent
    data class SelectHabitType(val habitType: ModeForSwitch): AddHabitEvent
}