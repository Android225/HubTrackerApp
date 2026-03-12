package com.example.hubtrackerapp.presentation.screens.clubs.add_club

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.data.predefined.PredefinedHabitData
import com.example.hubtrackerapp.domain.hubbit.models.AddHabitMode
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.hubbit.models.club.model.Club
import com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club.CreateClubUseCase
import com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club.GetClubByIdUseCase
import com.example.hubtrackerapp.domain.hubbit.models.club.use_case.club.UpdateClubUseCase
import com.example.hubtrackerapp.presentation.screens.add.AddHabitEvent
import com.example.hubtrackerapp.presentation.screens.add.AddHabitState
import com.example.hubtrackerapp.presentation.screens.add.AddHabitViewModel
import com.example.hubtrackerapp.presentation.screens.add.PickerType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel(assistedFactory = AddClubViewModel.Factory::class)
class AddClubViewModel @AssistedInject constructor(
    private val getClubByIdUseCase: GetClubByIdUseCase,
    private val createClubUseCase: CreateClubUseCase,
    private val updateClubUseCase: UpdateClubUseCase,
    @Assisted("editData") private val editData: String? = null
): ViewModel(){
    val predefinedHabits = PredefinedHabitData.habits
    private val _state = MutableStateFlow<AddClubState>(AddClubState.Form(isEditMode = (if (editData != null) AddHabitMode.EDIT_EXISTING else AddHabitMode.CREATE_NEW)))
    val state = _state.asStateFlow()

    init {
        if (editData != null){
            loadClubData()
        }
    }

    private fun loadClubData() {
        viewModelScope.launch {
            _state.update { currentState ->
                if (currentState is AddClubState.Form) {
                    currentState.copy(isLoading = true)
                } else {
                    currentState
                }
            }

            try {
                val club = getClubByIdUseCase(editData!!)
                if (club != null) {
                    _state.update { currentState ->
                        if (currentState is AddClubState.Form) {
                            currentState.copy(
                                clubId = club.clubId,
                                title = club.title,
                                description = club.description,
                                imageUrl = club.imageUrl,
                                category = club.category,
                                isPrivate = club.isPrivate,
                                isLoading = false,
                                createdAt = club.createdAt,
                                adminId = club.adminId
                            )
                        } else {
                            currentState
                        }
                    }
                } else {
                    _state.update { currentState ->
                        if (currentState is AddClubState.Form) {
                            currentState.copy(error = "Club not found", isLoading = false)
                        } else {
                            currentState
                        }
                    }
                }
            } catch (e: Exception) {
                _state.update { currentState ->
                    if (currentState is AddClubState.Form) {
                        currentState.copy(error = e.message, isLoading = false)
                    } else {
                        currentState
                    }
                }
            }
        }
    }

    fun onEvent(event: AddClubEvent){
        when(event){
            is AddClubEvent.OnCategoryChange -> {
                _state.update { currentState ->
                    if (currentState is AddClubState.Form) {
                        currentState.copy(category = event.category)
                    } else {
                        currentState
                    }
                }
            }
            is AddClubEvent.OnDescriptionChange -> {
                _state.update { currentState ->
                    if (currentState is AddClubState.Form) {
                        currentState.copy(description = event.description)
                    } else {
                        currentState
                    }
                }
            }
            is AddClubEvent.OnImageUrlChange -> {
                _state.update { currentState ->
                    if (currentState is AddClubState.Form) {
                        currentState.copy(imageUrl = event.imageUrl)
                    } else {
                        currentState
                    }
                }
            }
            AddClubEvent.OnLoadClub -> {

            }
            is AddClubEvent.OnPrivateChange -> {
                _state.update { currentState ->
                    if (currentState is AddClubState.Form) {
                        currentState.copy(isPrivate = event.isPrivate)
                    } else {
                        currentState
                    }
                }
            }
            AddClubEvent.OnSaveClick -> {
                saveClub()
            }
            is AddClubEvent.OnTitleChange -> {
                _state.update { currentState ->
                    if (currentState is AddClubState.Form) {
                        currentState.copy(title = event.title)
                    } else {
                        currentState
                    }
                }
            }

            AddClubEvent.OnBackClick -> {  // 👈 Добавили событие для кнопки назад
                _state.update { AddClubState.Finished }
            }

            AddClubEvent.ClosePicker -> {
                _state.update {
                    it.updateCreation {
                        copy(activePicker = ClubPickerType.Close)
                    }
                }
            }
            is AddClubEvent.OpenPicker -> {
                _state.update {
                    it.updateCreation {
                        copy(activePicker = event.pickerType)
                    }
                }
            }

            is AddClubEvent.ApplyPredefinedCategory -> {
                _state.update {
                    it.updateCreation {
                        copy(imageUrl = event.habit.icon, category = event.habit.habitName)
                    }
                }
            }
        }

    }
    private inline fun AddClubState.updateCreation(
        crossinline update: AddClubState.Form.() -> AddClubState.Form
    ): AddClubState{
        return (this as? AddClubState.Form)?. update() ?: this
    }

    private fun saveClub() {
        viewModelScope.launch {
            val currentState = _state.value
            if (currentState !is AddClubState.Form) return@launch

            _state.update { currentState.copy(isSaving = true) }

            val result = if (currentState.isEditMode == AddHabitMode.EDIT_EXISTING) {
                updateClubUseCase(
                    Club(
                        clubId = currentState.clubId!!,
                        adminId = currentState.adminId!!,
                        title = currentState.title,
                        description = currentState.description,
                        imageUrl = currentState.imageUrl,
                        category = currentState.category,
                        createdAt = currentState.createdAt,
                        isPrivate = currentState.isPrivate,
                        lastUpdate = System.currentTimeMillis(),
                        memberCount = 0
                    )
                )
            } else {
                createClubUseCase(
                    title = currentState.title,
                    description = currentState.description,
                    imageUrl = currentState.imageUrl,
                    category = currentState.category,
                    isPrivate = currentState.isPrivate
                )
            }

            result.onSuccess {
                _state.update { AddClubState.Finished }  // 👈 Закрываем экран при успехе
            }.onFailure { error ->
                _state.update { currentState.copy(isSaving = false, error = error.message) }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("editData") editData: String? = null
        ): AddClubViewModel
    }
}

sealed class ClubPickerType {
    object Close : ClubPickerType()
    object Icon : ClubPickerType()
}

sealed interface AddClubState {
    data class Form(
        val isEditMode: AddHabitMode = AddHabitMode.CREATE_NEW,
        val clubId: String? = null,
        val adminId: String? = null,
        val createdAt: Long = 0,
        val title: String = "",
        val description: String = "",
        val imageUrl: String = "",
        val category: String = "",
        val isLoading: Boolean = false,
        val isSaving: Boolean = false,
        val error: String? = null,
        val isPrivate: Boolean = false,
        val activePicker: ClubPickerType = ClubPickerType.Close,
    ) : AddClubState

    data object Finished : AddClubState  // 👈 Добавили Finished
}

sealed interface AddClubEvent{
    data class OpenPicker(val pickerType: ClubPickerType) : AddClubEvent
    object ClosePicker : AddClubEvent
    data class OnTitleChange(val title: String) : AddClubEvent
    data class OnDescriptionChange(val description: String) : AddClubEvent
    data class OnImageUrlChange(val imageUrl: String) : AddClubEvent
    data class OnCategoryChange(val category: String) : AddClubEvent
    data class OnPrivateChange(val isPrivate: Boolean) : AddClubEvent
    data class ApplyPredefinedCategory(val habit: PredefinedHabit) : AddClubEvent
    data object OnSaveClick : AddClubEvent
    data object OnLoadClub : AddClubEvent // для загрузки данных при редактировании
    data object OnBackClick : AddClubEvent
}