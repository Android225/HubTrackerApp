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
import com.example.hubtrackerapp.data.HabitRepositoryImpl
import com.example.hubtrackerapp.domain.hubbit.GetHabitsWithScheduleForDateUseCase
import com.example.hubtrackerapp.domain.hubbit.SwitchCompleteStatusUseCase
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
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

class HomeViewModel : ViewModel() {

    private val repository = HabitRepositoryImpl
    private val getHabitsWithScheduleForDateUseCase =
        GetHabitsWithScheduleForDateUseCase(repository = repository)
    private val switchCompleteStatusUseCase =
        SwitchCompleteStatusUseCase(repository)
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
//        getHabitsWithScheduleForDateUseCase(repository.testUser.userId, selectedDate.value)
//            .onEach { habits ->
//
//                _state.update { habit ->
//                    habit.copy(
//                        selectedDate = _selectedDate.value,
//                        calendarDays = _calendarDays.value,
//                        habits = habits,
//                        completedCount = habits.count { it.isCompleted },
//                        mode = ModeForSwitch.HOBBIES,
//                        isLoading = false
//                    )
//                }
//            }.launchIn(viewModelScope)


        selectedDate
            .onEach { date ->
                _state.update {
                    it.copy(
                        selectedDate = date,
                        calendarDays = _calendarDays.value,
                        isLoading = true
                    )
                }
            }
            .flatMapLatest { date ->
                getHabitsWithScheduleForDateUseCase(
                    repository.testUser.userId,
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

    private val _modeSwitcher = MutableStateFlow(ModeForSwitch.HOBBIES)
    val mode = _modeSwitcher.asStateFlow()


    fun changeMode(newMode: ModeForSwitch) {
        _modeSwitcher.value = newMode
    }


    private fun onDateChanged(date: LocalDate) {
        _selectedDate.value = date
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
                is HabitCommands.FailHabitInThisDay -> TODO()
                is HabitCommands.SkipHabitInThisDay -> TODO()
                is HabitCommands.SwitchCompletedStatus -> {
                    switchCompleteStatusUseCase(command.habitId, _selectedDate.value)
                }

                is HabitCommands.ChangeDate -> {
                    onDateChanged(command.date)
                }
            }
        }
    }

}

data class HomeUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarDays: List<CalendarDayUi> = emptyList(),
    val habits: List<HabitWithProgressUi> = emptyList(),
    val completedCount: Int = 0,
    val mode: ModeForSwitch = ModeForSwitch.HOBBIES,
    val isLoading: Boolean = false // состояние загружены ли данные true = загружаются false = загружены
)

sealed interface HabitCommands {
    data class ChangeDate(val date: LocalDate) : HabitCommands
    data class SwitchCompletedStatus(override val habitId: String) : HabitAction
    data class SkipHabitInThisDay(override val habitId: String) : HabitAction
    data class FailHabitInThisDay(override val habitId: String) : HabitAction
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
