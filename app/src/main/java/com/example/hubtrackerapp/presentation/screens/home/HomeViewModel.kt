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
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

object HomeViewModel: ViewModel(){

    private val repository = HabitRepositoryImpl
    private val getHabitsWithScheduleForDateUseCase = GetHabitsWithScheduleForDateUseCase(repository = repository)

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()


    //текущая дата
    private val today: LocalDate = LocalDate.now()

    //выбранная дата в списке
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()

    private val _calendarDays = MutableStateFlow<List<CalendarDayUi>>(emptyList())
    val calendarDays = _calendarDays.asStateFlow()

    init {

        generateMonth()
        viewModelScope.launch {
            loadingInitialData()
        }
    }
    private val _modeSwitcher = MutableStateFlow(ModeForSwitch.HOBBIES)
    val mode = _modeSwitcher.asStateFlow()
    private suspend fun loadingInitialData(){
        getHabitsWithScheduleForDateUseCase(repository.testUser.userId, selectedDate.value)
            .onEach { habits ->
                _state.update {
                    it.copy(
                        selectedDate = selectedDate.value,
                        calendarDays = calendarDays.value,
                        habits = habits,
                        completedCount = habits.count{it.isCompleted},
                        mode = mode.value,
                        isLoading = false
                    )
                }
            }.launchIn(viewModelScope)
    }
    fun changeMode(newMode: ModeForSwitch){
        _modeSwitcher.value = newMode
    }


    fun onDateSelected(date: LocalDate){
        _selectedDate.value = date
    }
    private fun generateMonth(){
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


}

data class HomeUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val calendarDays: List<CalendarDayUi> = emptyList(),
    val habits: List<HabitWithProgressUi> = emptyList(),
    val completedCount: Int = 0,
    val mode: ModeForSwitch = ModeForSwitch.HOBBIES,
    val isLoading: Boolean = false // состояние загружены ли данные true = загружаются false = загружены
)

//потом в реализации аватарок на urls заменить List<ImageBitmap>
@Composable
fun fakeParticipants(): List<ImageBitmap> {
    val avatar = ImageBitmap.imageResource(
        id = R.drawable.image_cat_test_avatar
    )
    Log.d("FAKE","GET AVATARS")
    return List(5) { avatar }
}
