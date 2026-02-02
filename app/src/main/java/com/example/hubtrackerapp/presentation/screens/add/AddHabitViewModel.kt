package com.example.hubtrackerapp.presentation.screens.add

//import com.example.hubtrackerapp.data.HabitRepositoryImpl
import HabitMetric
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.domain.hubbit.AddHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.EditHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.GetHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.GetUserID
import com.example.hubtrackerapp.domain.hubbit.models.AddHabitMode
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.predefined.GetAllPredefinedHabitsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@HiltViewModel(assistedFactory = AddHabitViewModel.Factory::class)
class AddHabitViewModel @AssistedInject constructor(
    private val addHabitUseCase:AddHabitUseCase,
    private val getAllPredefinedHabitsUseCase:GetAllPredefinedHabitsUseCase,
    private val getHabitUseCase: GetHabitUseCase,
    private val getUserID: GetUserID,
    private val editHabitUseCase: EditHabitUseCase,
    //val predefinedHabits: List<PredefinedHabit>
    @Assisted("editData") private val editData: String? = null
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
                when {
                    // 1. Обычное создание карточки
                    editData == null -> AddHabitState.Creation(
                        form = getAllPredefinedHabitsUseCase().first(),
                        mode = AddHabitMode.CREATE_NEW
                    )

                    // 2. Predefined хобби
                    predefinedHabits.any { it.habitName == editData } -> {
                        val predefined = predefinedHabits.first { it.habitName == editData }
                        AddHabitState.Creation(
                            form = predefined,
                            mode = AddHabitMode.CREATE_FROM_PREDEFINED
                        )
                    }

                    // 3. Редактирование существующего хобби
                    else -> {
                        try {
                            val userId = getUserID()
                            val habit = getHabitUseCase(userId, editData)

                            val predefinedForm = PredefinedHabit(
                                habitName = habit.title,
                                icon = habit.emoji,
                                color = habit.color,
                                metricForHabit = habit.metric,
                                target = habit.target,
                                habitSchedule = habit.schedule,
                                reminderTime = habit.reminderTime,
                                reminderDate = habit.reminderDate,
                                reminderIsActive = habit.reminderIsActive,
                                habitType = habit.habitType,
                                habitCustom = habit.habitCustom
                            )

                            AddHabitState.Creation(
                                form = predefinedForm,
                                mode = AddHabitMode.EDIT_EXISTING,
                                habitId = editData // ✅ Сохраняем ID
                            )
                        } catch (e: Exception) {
                            Log.e("AddHabitViewModel", "Error loading habit: ${e.message}")
                            AddHabitState.Creation(
                                form = getAllPredefinedHabitsUseCase().first(),
                                mode = AddHabitMode.CREATE_NEW
                            )
                        }
                    }
                }
            }
        }
    }
//        viewModelScope.launch {
//            _state.update {
//                AddHabitState.Creation(
//                    form = getAllPredefinedHabitsUseCase().first()
//                )
//            }
//        }

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
                        if (previous is AddHabitState.Creation && previous.isSaveEnable) {
                            when (previous.mode) {
                                AddHabitMode.EDIT_EXISTING -> {
                                    // Редактирование существующего хобби
                                    val habitUi = HabitUi(
                                        habitId = previous.habitId!!, // Не null в режиме редактирования
                                        userId = getUserID(),
                                        emoji = previous.form.icon,
                                        title = previous.form.habitName,
                                        createdAt = LocalDate.now(), // Нужно сохранить оригинальную дату!
                                        schedule = previous.form.habitSchedule,
                                        color = previous.form.color,
                                        target = previous.form.target,
                                        metric = previous.form.metricForHabit,
                                        reminderTime = previous.form.reminderTime,
                                        reminderDate = previous.form.reminderDate,
                                        reminderIsActive = previous.form.reminderIsActive,
                                        habitType = previous.form.habitType,
                                        habitCustom = previous.form.habitCustom
                                    )

                                    // Вызываем use case для редактирования
                                    editHabitUseCase(habitUi)
                                }

                                AddHabitMode.CREATE_NEW,
                                AddHabitMode.CREATE_FROM_PREDEFINED -> {
                                    // Создание нового хобби
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
                                }
                            }

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
    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("editData") editData: String? = null
        ): AddHabitViewModel
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
        val mode: AddHabitMode = AddHabitMode.CREATE_NEW,
        val habitId: String? = null,
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