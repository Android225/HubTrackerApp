package com.example.hubtrackerapp.presentation.screens.explore

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.lifecycle.ViewModel
import com.example.hubtrackerapp.R
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(

): ViewModel(){
    val predefinedHabits = PredefinedHabitData.habits
}

data class ExploreUiState(
    val searchQuery: String = "",
    val habits: List<PredefinedHabit> = emptyList(),
    val clubs: List<Club> = emptyList(),
    val challenges: List<Challenge> = emptyList(),
    val infoLearning: List<Learning> = emptyList(),
    val isLoading: Boolean = false,
    // val error: String? = null
)

sealed interface ExploreEvent {

    data object PredefinedHabitClick: ExploreEvent
    data object ClubClick: ExploreEvent
    data object ChallengeClick: ExploreEvent
    data object LearningClick: ExploreEvent
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