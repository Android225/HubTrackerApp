package com.example.hubtrackerapp.presentation.screens.add

import HabitMetric
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.hubtrackerapp.data.HabitRepositoryImpl
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.data.predefined.PredefinedHabitRepositoryImpl
import com.example.hubtrackerapp.domain.hubbit.AddHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.GetAllPredefinedHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddHabitViewModel @Inject constructor(
    private val addHabitUseCase:AddHabitUseCase,
    private val getAllPredefinedHabitsUseCase:GetAllPredefinedHabitsUseCase,
    //val predefinedHabits: List<PredefinedHabit>
) : ViewModel() {

    /*
    * можно будет потом добавить в конструктор habitId: String будем получать карточку хобби
    *
    * на весить @AssistedInject
    *
    * @Assisted("habitId") private val habitId: String
    *
    * и в классе ViewModel создать фабрику
    *
    * @AssistedFactory
    * interface Factory {
    * fun create(
    * @Assisted("habitId")habitId: String
    * ): AddHabitViewModel
    * }
    *
    * @HiltViewModel(assistedFactory = AddHabitViewModel.Factory::class)
    *
    *
    * в AddHabit hiltViewModel(
    * creationCallback = {factory: AddHabitViewModel.Factory ->
    * factory.create(habitId)
    * })
    * */


    //private val repository = HabitRepositoryImpl
    //private val addHabitUseCase = AddHabitUseCase(repository)
    //private val getAllPredefinedHabitsUseCase = GetAllPredefinedHabitsUseCase(predefinedRepository)
   // private val predefinedRepository = PredefinedHabitRepositoryImpl
    val predefinedHabits = PredefinedHabitData.habits
    private val _state = MutableStateFlow<AddHabitState>(AddHabitState.Initial)
    val state = _state.asStateFlow()
   init {
        viewModelScope.launch {
            _state.update {
                AddHabitState.Creation(
                    form = getAllPredefinedHabitsUseCase().first()
                )
            }
        }
   }
    private inline fun AddHabitState.updateCreation(
        crossinline update: AddHabitState.Creation.() -> AddHabitState.Creation
    ): AddHabitState{
    return (this as? AddHabitState.Creation)?. update() ?: this
    }
    fun onEventAddHabit(event: AddHabitEvent) {
        when (event) {

            is AddHabitEvent.NameChanged -> {
                Log.d("AddHabit","Name Changed -> ${event.habitName}")
                _state.update {
                    it.updateCreation {
                        copy(form = form.copy(habitName = event.habitName))
                    }
                }
            }

            is AddHabitEvent.SelectColor -> {
                _state.update { previous ->
                    previous.updateCreation {
                        copy(form = form.copy(color = event.color),activePicker = PickerType.Close)
                    }
                }
            }

            is AddHabitEvent.SelectHabitSchedule -> {
                _state.update {
                    it.updateCreation {
                        copy(form = form.copy(habitSchedule = event.habitSchedule))
                    }
                }
            }

            is AddHabitEvent.SelectIcon -> {
                val newIcon = event.icon
                    .trim()
                    .let { text ->
                        if (text.isEmpty()) ""
                        else text.codePoints().limit(1).toArray()
                            .let { String(it, 0, it.size) }
                    }
                _state.update {
                    it.updateCreation {
                        copy(form = form.copy(icon = newIcon, habitCustom = true))
                    }
                }
            }

            is AddHabitEvent.SelectMetric -> {
                val filteredTarget = event.target
                    .filter { it.isDigit() }          // оставляем только цифры
                    .take(3)                          // не больше 3 символов
                    .trimStart('0')                   // убираем ведущие нули

                _state.update {
                    it.updateCreation {
                        copy(form = form.copy(metricForHabit = event.metric, target = filteredTarget))
                    }
                }

            }

            is AddHabitEvent.SelectTimeAndDate -> {
                _state.update {
                    it.updateCreation {
                        copy(
                            form = form.copy(reminderTime = event.reminderTime, reminderDate = event.reminderDate),
                            activePicker = PickerType.Close)
                    }
                }
            }

            is AddHabitEvent.ApplyPredefinedHabit -> {
                _state.update {
                    it.updateCreation {
                        copy(form = event.habit)
                    }
                }
            }

            is AddHabitEvent.SelectHabitType -> {
                _state.update {
                    it.updateCreation {
                        copy(form = form.copy(habitType = event.habitType))
                    }
                }
            }

            AddHabitEvent.SwitchReminder -> {
                _state.update {
                    it.updateCreation {
                        copy(form = form.copy(reminderIsActive = !form.reminderIsActive))
                    }
                }
            }

            is AddHabitEvent.OpenPicker -> {
                _state.update {
                    it.updateCreation {
                        copy(activePicker = event.pickerType)
                    }
                }
            }

            AddHabitEvent.ClosePicker -> {
                _state.update {
                    it.updateCreation {
                        copy(activePicker = PickerType.Close)
                    }
                }
            }

            AddHabitEvent.Save -> {
                viewModelScope.launch {
                    _state.update { previous ->
                        if (previous is AddHabitState.Creation && previous.isSaveEnable){
                            addHabitUseCase(
                                emoji = previous.form.icon,
                                title = previous.form.habitName,
                                schedule = previous.form.habitSchedule,
                                color = previous.form.color,
                                target = previous.form.target,
                                metric = previous.form.metricForHabit,
                                reminderTime = previous.form.reminderTime,
                                reminderDate = previous.form.reminderDate,
                                reminderIsActive = previous.form.reminderIsActive,
                                habitType = previous.form.habitType,
                                habitCustom = previous.form.habitCustom,
                                createdAt = LocalDate.now()
                            )
                            AddHabitState.Finished
                        } else {
                            previous
                        }
                    }
                }
            }

            AddHabitEvent.Back -> {
                _state.update {
                    AddHabitState.Finished
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

sealed interface AddHabitState{
    data object Initial: AddHabitState // поле form в Creation в самом начале не инициализировано
    // поэтому вызываем сначала Initaial
    data class Creation(
        val form: PredefinedHabit,
        val activePicker: PickerType = PickerType.Close,
    ) : AddHabitState {
        val isSaveEnable: Boolean
            get() {
                return when {
                    form.habitName.isBlank() -> false
                    (form.target.toIntOrNull() ?: 0) < 1 -> false
                    else -> {
                        Log.d("AddHabit","IsSave Enable to Button Save is true")
                        true
                    }
                }
            }
    }
    data object Finished : AddHabitState
}


    sealed interface AddHabitEvent {

        object SwitchReminder : AddHabitEvent
        object ClosePicker : AddHabitEvent

        data object Save: AddHabitEvent
        data object Back : AddHabitEvent
        data class OpenPicker(val pickerType: PickerType) : AddHabitEvent
        data class NameChanged(val habitName: String) : AddHabitEvent
        data class SelectIcon(val icon: String) : AddHabitEvent
        data class SelectColor(val color: Color) : AddHabitEvent

        data class ApplyPredefinedHabit(val habit: PredefinedHabit) : AddHabitEvent

        data class SelectMetric(val metric: HabitMetric, val target: String) : AddHabitEvent
        data class SelectHabitSchedule(val habitSchedule: HabitSchedule) : AddHabitEvent
        data class SelectTimeAndDate(val reminderTime: LocalTime, val reminderDate: HabitSchedule) : AddHabitEvent

        data class SelectHabitType(val habitType: ModeForSwitchInHabit) : AddHabitEvent
    }