@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.hubtrackerapp.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.R
//import com.example.hubtrackerapp.data.HabitRepositoryImpl
import com.example.hubtrackerapp.domain.hubbit.AddHabitUseCase
import com.example.hubtrackerapp.domain.hubbit.GetHabitsWithScheduleForDateUseCase
import com.example.hubtrackerapp.domain.hubbit.GetUserCardUseCase
import com.example.hubtrackerapp.domain.hubbit.GetUserID
import com.example.hubtrackerapp.domain.hubbit.SaveProgressUseCase
import com.example.hubtrackerapp.domain.hubbit.SwitchCompleteStatusUseCase
import com.example.hubtrackerapp.domain.hubbit.SwitchFailStatusUseCase
import com.example.hubtrackerapp.domain.hubbit.SwitchSkipStatusUseCase
import com.example.hubtrackerapp.domain.hubbit.models.HabitProgress
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.SwipeHabitState
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.hubbit.models.forUi.Mood
import com.example.hubtrackerapp.presentation.screens.add.PickerType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitsWithScheduleForDateUseCase:GetHabitsWithScheduleForDateUseCase,
    private val getUserId:GetUserID,
    private val getUserCard:GetUserCardUseCase,
    private val switchCompleteStatusUseCase:SwitchCompleteStatusUseCase,
    private val switchFailStatusUseCase: SwitchFailStatusUseCase,
    private val switchSkipStatusUseCase: SwitchSkipStatusUseCase,
    private val saveProgressUseCase: SaveProgressUseCase
) : ViewModel() {

    //private val repository = HabitRepositoryImpl
//    private val getHabitsWithScheduleForDateUseCase =
//        GetHabitsWithScheduleForDateUseCase(repository = repository)
   // private val getUserId = GetUserID(repository)
    //private val getUserCard = GetUserCardUseCase(repository)
//    private val switchCompleteStatusUseCase =
//        SwitchCompleteStatusUseCase(repository)
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()


    //текущая дата
    private val today: LocalDate = LocalDate.now()

    //выбранная дата в списке от нее обновление UI
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _calendarDays = MutableStateFlow<List<CalendarDayUi>>(emptyList())
    val calendarDays = _calendarDays.asStateFlow()

    init {
        generateMonth()
        selectedDate
            .onEach { date ->
                _state.update {
                    it.copy(
                        userName = getUserCard(getUserId()).firstName,
                        selectedDate = date,
                        calendarDays = _calendarDays.value,
                        isLoading = true
                    )
                }
            }
            .flatMapLatest { date ->
                getHabitsWithScheduleForDateUseCase(
                    getUserId(),
                    //repository.testUser.userId,
                    date
                )
            }
            .onEach { habits ->
                _state.update {
                    it.copy(
                        habits = habits,
                        completedCount = habits.count { it.isCompleted },
                        isLoading = false
                    )
                }
            }.launchIn(viewModelScope)
    }
    private fun generateMonth() {
        val firstDayInMonth = today.withDayOfMonth(1)
        val lastDayInMonth = today.with(TemporalAdjusters.lastDayOfMonth())

        _calendarDays.value = (1..lastDayInMonth.dayOfMonth).map {
            val date = firstDayInMonth.withDayOfMonth(it)
            CalendarDayUi(
                date = date,
                dayNumber = it,
                dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                isToday = date == today
            )
        }

    }

    fun processCommand(command: HabitCommands) {
        viewModelScope.launch {
            when (command) {
                is HabitCommands.FailHabitInThisDay -> {
                    switchFailStatusUseCase(command.habitId, _selectedDate.value)
                }
                is HabitCommands.SkipHabitInThisDay -> {
                    switchSkipStatusUseCase(command.habitId, _selectedDate.value)
                }
                is HabitCommands.SwitchCompletedStatus -> {
                    switchCompleteStatusUseCase(command.habitId, _selectedDate.value)
                }
//                is HabitCommands.ChangeDate -> {
//                    onDateChanged(command.date)
//                }
                is HabitCommands.OnHabitSwiped -> {
                    _state.update { state ->
                        state.copy(
                            habits = state.habits.map { habit ->
                                if (habit.habitId == command.habitId){
                                    habit.copy(swipeState = command.newState)
                                } else {
                                    habit.copy(swipeState = SwipeHabitState.CLOSED)
                                }
                            }
                        )
                    }
                }

                HabitCommands.ApplyEditor -> {
                    val edit = state.value.editProgressState ?: return@launch

                    viewModelScope.launch {
                        val progressPercent = edit.percent
                        val isCompleted = progressPercent >= 1f

                        saveProgressUseCase(
                            HabitProgress(
                                habitId = edit.habitId,
                                date = selectedDate.value,
                                isCompleted = isCompleted,
                                progress = progressPercent,
                                progressWithTarget = edit.tempProgress.toString(),
                                skipped = false,
                                failed = false
                            )
                        )

                        _state.update { state ->
                            state.copy(
                                editProgressState = null,
                                habits = state.habits.map {
                                    if (it.habitId == edit.habitId) {
                                        it.copy(
                                            progress = progressPercent,
                                            progressWithTarget = edit.tempProgress.toString(),
                                            isCompleted = isCompleted,
                                            isInTargetMode = false
                                        )
                                    } else it
                                }
                            )
                        }
                    }
                }
                HabitCommands.CancelEditor -> {
                    _state.update { state ->
                        state.copy(
                            editProgressState = null,
                            habits = state.habits.map { it.copy(isInTargetMode = false) }
                        )
                    }
                }
                is HabitCommands.OpenEditor -> {
                    _state.update { state ->
                        val habit = state.habits.first { it.habitId == command.habitId }

                        state.copy(
                            editProgressState = EditProgressState(
                                habitId = habit.habitId,
                                tempProgressText = habit.progressWithTarget,
                                target = habit.target.toIntOrNull() ?: 1
                            ),
                            habits = state.habits.map {
                                it.copy(isInTargetMode = it.habitId == command.habitId)
                            }
                        )
                    }
                }
                is HabitCommands.UpdateEditor -> {
                    val edit = state.value.editProgressState ?: return@launch

                    if (command.newValue.isEmpty()) {
                        _state.update {
                            it.copy(
                                editProgressState = edit.copy(
                                    tempProgressText = ""
                                )
                            )
                        }
                        return@launch
                    }

                    if (!command.newValue.all { it.isDigit() }) {
                        return@launch
                    }

                    val withoutLeadingZeros = command.newValue.trimStart('0')
                    val finalValue = if (withoutLeadingZeros.isEmpty()) "0" else withoutLeadingZeros

                    val number = finalValue.toInt()
                    if (number > edit.target) {

                        _state.update {
                            it.copy(
                                editProgressState = edit.copy(
                                    tempProgressText = edit.target.toString()
                                )
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                editProgressState = edit.copy(
                                    tempProgressText = finalValue
                                )
                            )
                        }
                    }
                }
            }
        }
    }
    fun onEvent(event: HomeEvent){
        when(event) {
            HomeEvent.AddClicked -> {
                _state.update { it.copy(addMenuVisible = true) }
            }
            HomeEvent.DismissAddMenu -> {
                _state.update { it.copy(addMenuVisible = false) }
            }
            is HomeEvent.ChangeModeScreen -> {
                Log.d("Home","Mode Changed -> ${event.newMode}")
                _state.update {
                    it.copy(mode = event.newMode)
                }
            }
            is HomeEvent.OnDateChanged -> {
                _selectedDate.value = event.date
            }
            is HomeEvent.SelectEmojiInProfile -> {
                _state.update {
                    it.copy(mood = event.mood)
                }
            }

            is HomeEvent.OpenPicker -> {
                _state.update {
                    it.copy(activeHomePicker = event.pickerType)
                }
            }

            HomeEvent.ClosePicker -> {
                _state.update {
                    it.copy(activeHomePicker = HomePickerType.Close)
                }
            }

            HomeEvent.ActivityClick -> {
                Log.d("Home","Bottom bar Navigate to activity")
            }
            HomeEvent.ExploreClick -> {
                Log.d("Home","Bottom bar Navigate to explore")
            }
            HomeEvent.ProfileClick -> {
                Log.d("Home","Bottom bar Navigate to profile")
            }
        }
    }
}

sealed class HomePickerType {
    object Close: HomePickerType()
    object Calendar: HomePickerType()
    object Notifications: HomePickerType()
    object EmojiBar: HomePickerType()
    object AddHabit: HomePickerType()

    object ViewHabitCard: HomePickerType()

}

data class EditProgressState(
    val habitId: String = "0",
    val tempProgressText: String,
    val target: Int
){
    val tempProgress: Int
        get() = tempProgressText.toIntOrNull() ?: 0

    val percent: Float
        get() = (tempProgress.toFloat() / target)
            .coerceIn(0f, 1f)
}
data class HomeUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarDays: List<CalendarDayUi> = emptyList(),
    val habits: List<HabitWithProgressUi> = emptyList(),
    val completedCount: Int = 0,
    val mode: ModeForSwitch = ModeForSwitch.HOBBIES,
    val isLoading: Boolean = false, // состояние загружены ли данные true = загружаются false = загружены
    val addMenuVisible: Boolean = false,
    val activeHomePicker: HomePickerType = HomePickerType.Close,
    val userName: String = "",
    val mood: Mood = Mood.Happy,
    val editProgressState: EditProgressState? = null
    //val clubs: List<ClubUI>
    //val challenges: List<ChallengesUI>
)

sealed interface HabitCommands {
   // data class ChangeDate(val date: LocalDate) : HabitCommands
    data class SwitchCompletedStatus(override val habitId: String) : HabitAction

    //добавление прогресса
    data class OpenEditor(val habitId: String) : HabitCommands
    data class UpdateEditor(val newValue: String) : HabitCommands
    object ApplyEditor : HabitCommands
    object CancelEditor : HabitCommands
    //взаимодействие со swipe
    data class SkipHabitInThisDay(override val habitId: String) : HabitAction
    data class FailHabitInThisDay(override val habitId: String) : HabitAction
    data class OnHabitSwiped(override val habitId: String,val newState: SwipeHabitState):  HabitAction
}

sealed interface HomeEvent{
    data object AddClicked: HomeEvent
    data object DismissAddMenu: HomeEvent
    data object ExploreClick: HomeEvent
    data object ActivityClick: HomeEvent
    data object ProfileClick: HomeEvent

    data object ClosePicker: HomeEvent
    data class ChangeModeScreen(val newMode: ModeForSwitch): HomeEvent
    data class SelectEmojiInProfile(val mood: Mood): HomeEvent
    data class OnDateChanged(val date: LocalDate): HomeEvent
    data class OpenPicker(val pickerType: HomePickerType): HomeEvent

}

sealed interface HabitAction : HabitCommands {
    val habitId: String
}

//потом в реализации аватарок на urls заменить List<ImageBitmap>
@Composable
fun fakeParticipants(): List<ImageBitmap> {
    val avatar = ImageBitmap.imageResource(
        id = R.drawable.image_cat_test_avatar
    )
    Log.d("FAKE", "GET AVATARS")
    return List(5) { avatar }
}
