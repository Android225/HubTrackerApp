@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.clubs.add_club

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit

import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.presentation.screens.add.AddHabitEvent
import com.example.hubtrackerapp.presentation.screens.components.AddAndEditContent
import com.example.hubtrackerapp.presentation.screens.components.ChoiceParameters
import com.example.hubtrackerapp.presentation.screens.components.IconPickerContentComp
import com.example.hubtrackerapp.presentation.screens.components.ModSwitcher
import com.example.hubtrackerapp.presentation.screens.components.SwitcherOption

import com.example.hubtrackerapp.presentation.screens.components.TextContent
import com.example.hubtrackerapp.presentation.screens.components.TextStr
import com.example.hubtrackerapp.presentation.screens.components.UniversalAnimatedPickerComp


@Composable
fun AddClubScreen(
    modifier: Modifier = Modifier,
    editData: String? = null,
    viewModel: AddClubViewModel = hiltViewModel(
        creationCallback = {factory: AddClubViewModel.Factory ->
            factory.create(editData)

        }
    ),
    onBackClick: () -> Unit
){

    val state by viewModel.state.collectAsState()
    val predefinedHabits = viewModel.predefinedHabits

    when(state){
        is AddClubState.Form -> {
            val creationState = state as AddClubState.Form
            AddAndEditContent(
                modifier = modifier,
                nameForScreen = "club",
                form = creationState,
                mode = creationState.isEditMode,
                backEvent = AddClubEvent.OnBackClick,
                saveEvent = AddClubEvent.OnSaveClick,
                onEvent = { viewModel.onEvent(it as AddClubEvent) },
            ) {ClubScreenContent(
                modifier = modifier,
                form = creationState,
                innerPadding = it,
                onEvent = viewModel::onEvent
            ) }
            PickerOverlay(
                form = creationState,
                predefinedHabits = predefinedHabits,
                activePicker = creationState.activePicker,
                onEvent = viewModel::onEvent
            )
        }
        AddClubState.Finished -> {
            LaunchedEffect(Unit) {
                onBackClick()
            }
        }
    }
}

@Composable
private fun ClubScreenContent(
    modifier: Modifier,
    form: AddClubState.Form,
    innerPadding: PaddingValues,
    onEvent: (AddClubEvent) -> Unit
){
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(4.dp))
        TextStr(text = "Club title")
        TextContent(
            text = form.title,
            textPlace = "Enter club title",
            onTextChanged = {
                onEvent(AddClubEvent.OnTitleChange(it))
                Log.d("AddClub", "Event AddClubEvent.OnTitleChange")
            }
        )
        TextStr(text = "Club description")
        TextContent(
            text = form.description,
            textPlace = "Enter club description",
            onTextChanged = {
                onEvent(AddClubEvent.OnDescriptionChange(it))
                Log.d("AddClub", "AddClubEvent.OnDescriptionChange")
            }
        )
        TextStr(text = "Club category")
        TextContent(
            text = form.category,
            textPlace = "Enter club category",
            onTextChanged = {
                onEvent(AddClubEvent.OnCategoryChange(it))
                Log.d("AddClub", "AddClubEvent.OnCategoryChange")
            }
        )
        TextStr(text = "ICON AND CATEGORY")
        ChoiceParameters(
            habitName = form.category,
            choiceName = "Icon",
            icon = form.imageUrl,
            onClick = {
                onEvent(AddClubEvent.OpenPicker(ClubPickerType.Icon))
                Log.d("AddClub", "AddClubEvent.OpenPicker(ClubPickerType.Icon))")
            }
        )
        TextStr(text = "Change Visible")
        ModSwitcher(
            modifier = Modifier.offset(y = (-64).dp),
            selected = form.isPrivate,
            onModChange = {
                onEvent(AddClubEvent.OnPrivateChange(it))
                Log.d("AddClub", "AddClubEvent.OnPrivateChange($it)")
            },
            options = listOf(
                SwitcherOption("Open", false),
                SwitcherOption("Private", true)
            ),
            horizontalPadding = 0.dp
        )
        TextStr(text = "Add friends")
    }
}

@Composable
private fun PickerOverlay(
    form: AddClubState.Form,
    predefinedHabits: List<PredefinedHabit>,
    activePicker: ClubPickerType,
    onEvent: (AddClubEvent) -> Unit
){
    when (activePicker) {
        ClubPickerType.Close -> {}
        ClubPickerType.Icon -> {
            UniversalAnimatedPickerComp(
                pickerType = ClubPickerType.Icon,
                closeValue = ClubPickerType.Close,
                onDismissClick = {
                    onEvent(AddClubEvent.ClosePicker)
                },
                title = "Выбор своей иконки или готовое",
                content =
                    {
                        IconPickerContentComp(
                            text = form.imageUrl,
                            predefinedHabits = predefinedHabits,
                            onTextChanged = {
                                onEvent(AddClubEvent.OnImageUrlChange(it)) },
                            onAddHabit = {
                                onEvent(AddClubEvent.ApplyPredefinedCategory(it))
                                onEvent(AddClubEvent.ClosePicker)
                            },
                            onSaveIconClick = {
                                onEvent(AddClubEvent.ClosePicker)
                            }
                        )
                    },
            )
        }
    }
}