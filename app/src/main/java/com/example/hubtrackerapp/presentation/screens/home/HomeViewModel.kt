package com.example.hubtrackerapp.presentation.screens.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.ViewModel
import com.example.hubtrackerapp.R
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

object HomeViewModel: ViewModel(){

    //текущая дата
    val today: LocalDate = LocalDate.now()
    private val _calendarDays = MutableStateFlow<List<CalendarDayUi>>(emptyList())
    val calendarDays = _calendarDays.asStateFlow()

    init {
        generateMonth()
    }
    private val _modeSwitcher = MutableStateFlow(ModeForSwitch.HOBBIES)
    val mode = _modeSwitcher.asStateFlow()


    fun changeMode(newMode: ModeForSwitch){
        _modeSwitcher.value = newMode
    }

    private fun generateMonth(){
        val firstDayInMonth = today.withDayOfMonth(1)
        val lastDayInMonth = today.with(TemporalAdjusters.lastDayOfMonth())

        _calendarDays.value = (1..lastDayInMonth.dayOfMonth).map {
            val tempPlusDate = firstDayInMonth.plusDays(it.toLong())
            CalendarDayUi(
                date = tempPlusDate,
                dayNumber = it,
                dayOfWeek = tempPlusDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                isToday = tempPlusDate == today
            )
        }

    }


}
//потом в реализации аватарок на urls заменить List<ImageBitmap>
@Composable
fun fakeParticipants(): ImageAvatarsSized {
    val avatar = ImageBitmap.imageResource(
        id = R.drawable.image_cat_test_avatar
    )
    Log.d("FAKE","GET AVATARS")
    return ImageAvatarsSized(5,List(5) { avatar })
}
data class ImageAvatarsSized(
    val size: Int,
    val avatars: List<ImageBitmap>
)