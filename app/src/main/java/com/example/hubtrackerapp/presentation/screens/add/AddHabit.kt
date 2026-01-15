@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.add

import HabitMetric
import android.R
import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.presentation.theme.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.hubbit.models.toDisplayText
import com.example.hubtrackerapp.presentation.screens.components.ModSwitcher
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationViewModel
import com.example.hubtrackerapp.presentation.ui.theme.ColorGroup
import com.example.hubtrackerapp.presentation.ui.theme.colorGroups
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale


@Composable
fun AddHabit(
    modifier: Modifier = Modifier,
    viewModel: AddHabitViewModel = viewModel(
        // AddHabitViewModel()
    ),
    onBackClick: () -> Unit,
    onAddHabit: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val predefinedHabits = viewModel.predifinedHabits
    val uiState by viewModel.addHabitUiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    modifier = Modifier.padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 8.dp,
                        bottom = 16.dp
                    ),
                    title = {
                        Text(
                            text = "Create Custom Habit",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    color = Black10
                                )
                                .clickable {
                                    onBackClick()
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                modifier = Modifier
                                    .background(Color.Transparent),
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "button back"
                            )
                        }
                    }
                )
            },
            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 50.dp, top = 16.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        onClick = {
                            onAddHabit()
                        },
                        shape = RoundedCornerShape(40.dp),

                        ) {
                        Text(
                            text = "Add Habit"
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Black20)
            )

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.padding(4.dp))

                TextStr(text = "NAME")
                TextContent(
                    text = state.habitName,
                    textPlace = "Enter habit name",
                    onTextChanged = {
                        Log.d("habitName", "name - $it")
                        viewModel.setHabitName(it)
                    }
                )
                TextStr(text = "ICON AND COLOR")
                ChoiceIconAndColor(
                    modifier = Modifier.offset(y = (-12).dp),
                    habitName = state.habitName,
                    habitIcon = state.icon,
                    habitColor = state.color,
                    onColorClick = { viewModel.onEventAddHabit(AddHabitEvent.OpenPicker(PickerType.Color)) },
                    onIconClick = { viewModel.onEventAddHabit(AddHabitEvent.OpenPicker(PickerType.Icon)) }
                )
                TextStr(modifier = Modifier.offset(y = (-12).dp), text = "GOAL")
                ChoiceGoal(
                    modifier = Modifier.offset(y = (-24).dp),
                    metricForHabit = state.metricForHabit,
                    target = state.target,
                    onEditClick = {
                        viewModel.onEventAddHabit(AddHabitEvent.OpenPicker(PickerType.Goal))
                    },
                    onChoiceSchedule = {
                        viewModel.onEventAddHabit(
                            AddHabitEvent.OpenPicker(
                                PickerType.Schedule
                            )
                        )
                    },
                    habitSchedule = state.habitSchedule.toDisplayText()
                )
                TextStr(modifier = Modifier.offset(y = (-24).dp), text = "REMINDERS")
                ReminderBlock(
                    modifier = Modifier.offset(y = (-32).dp),
                    isEnabled = state.reminderIsActive,
                    reminderTime = state.reminderTime,
                    reminderDate = state.reminderDate,
                    onSwitchClick = {
                        viewModel.onEventAddHabit(AddHabitEvent.SwitchReminder)
                    },
                    onChoiceScheduleReminder = {
                        viewModel.onEventAddHabit(
                            AddHabitEvent.OpenPicker(
                                PickerType.Reminder
                            )
                        )
                    }
                )
                TextStr(modifier = Modifier.offset(y = (-40).dp), text = "HABIT TYPE")
                ModSwitcher(
                    modifier = Modifier.offset(y = (-64).dp),
                    selected = state.habitType,
                    onModChange = viewModel::changeMode,
                    textFirstSwitch = "Build",
                    textSecondSwitch = "Quit",
                    selectedFirst = ModeForSwitch.BUILD,
                    selectedSecond = ModeForSwitch.QUIT,
                    horizontalPadding = 0.dp
                )
            }
//        AnimatedPickerVisibility(
//            isVisible = uiState.isIconPickerVisible,
//            onDismiss = {},
//            onIconSelected = {}
//        )
        }
    }
    when (uiState.activePicker) {
        PickerType.Close -> {}
        PickerType.Color -> {
            UniversalAnimatedPicker(
                pickerType = PickerType.Color,
                onDismissClick = {
                    viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                },
                title = "Выбор цвета",
                content =
                    {
                        ColorPickerContent(
                            colorGroups = colorGroups,
                            selectedColor = state.color,
                            onColorSelected = {
                                viewModel.onEventAddHabit(
                                    AddHabitEvent.SelectColor(
                                        it
                                    )
                                )
                            }
                        )
                    }
            )
        }

        PickerType.Goal -> {
            UniversalAnimatedPicker(
                pickerType = PickerType.Goal,
                onDismissClick = {
                    if (state.target == "") {
                        viewModel.onEventAddHabit(
                            AddHabitEvent.SelectMetric(
                                metric = state.metricForHabit,
                                target = "1"
                            )
                        )
                    }
                    viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                },
                title = "Выбор метрик для хобби",
                content =
                    {
                        MetricPicker(
                            habitMetric = state.metricForHabit,
                            target = state.target,
                            onTargetChanged = { metric, target ->
                                viewModel.onEventAddHabit(
                                    AddHabitEvent.SelectMetric(
                                        metric = metric,
                                        target = target
                                    )
                                )
                            },
                            onSaveClick = {
                                if (state.target == "") {
                                    viewModel.onEventAddHabit(
                                        AddHabitEvent.SelectMetric(
                                            metric = state.metricForHabit,
                                            target = "1"
                                        )
                                    )
                                }
                                viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                            }
                        )
                    }
            )
        }

        PickerType.Icon -> {
            UniversalAnimatedPicker(
                pickerType = PickerType.Icon,
                onDismissClick = {
                    viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                },
                title = "Выбор своей иконки или готового хобби",
                content =
                    {
                        IconPickerContent(
                            text = state.icon,
                            predefinedHabits = predefinedHabits,
                            onTextChanged = { viewModel.onEventAddHabit(AddHabitEvent.SelectIcon(it)) },
                            onAddHabit = {
                                viewModel.onEventAddHabit(AddHabitEvent.ApplyPredefinedHabit(it))
                                viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                            },
                            onSaveIconClick = {
                                viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                            }
                        )
                    }
            )
        }

        PickerType.Schedule -> {
            UniversalAnimatedPicker(
                pickerType = PickerType.Schedule,
                onDismissClick = {
                    viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                },
                title = "График хобби",
                content =
                    {
                        SchedulePicker(
                            selectedValue = state.habitSchedule,
                            onValueChange = { value ->
                                viewModel.onEventAddHabit(
                                    AddHabitEvent.SelectHabitSchedule(value)
                                )
                            },
                            onCardClick = {
                                viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                            }
                        )
                    }
            )
        }

        PickerType.Reminder -> {
            UniversalAnimatedPicker(
                pickerType = PickerType.Reminder,
                onDismissClick = {
                    viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                },
                title = "График хобби",
                content =
                    {
                        TimePicker(
                            selectedValue = state.reminderDate,
                            selectedTime = state.reminderTime,
                            onValueChange = { date, time ->
                                viewModel.onEventAddHabit(
                                    AddHabitEvent.SelectTimeAndDate(
                                        reminderTime = time,
                                        reminderDate = date
                                    )
                                )
                            },
                            onCardClick = {
                                viewModel.onEventAddHabit(AddHabitEvent.ClosePicker)
                            }
                        )
                    }
            )
        }

    }

}

@Composable
private fun IconPickerContent(
    modifier: Modifier = Modifier,
    predefinedHabits: List<PredefinedHabit>,
    text: String,
    onTextChanged: (String) -> Unit,
    onAddHabit: (PredefinedHabit) -> Unit,
    onSaveIconClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = 24.dp
        )
    ) {
        item {
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomTextFieldForPicker(
                    modifier = Modifier
                        .weight(1f),
                    text = text,
                    textPlace = "Enter Icon",
                    onTextChanged = {
                        Log.d("Icon", "iconess - $it")
                        onTextChanged(it)
                    }

                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(Blue80)
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .clickable { onSaveIconClick() }
                ) {
                    Text(
                        text = "Сохранить иконку",
                        color = Blue10,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Black20)
            )
        }
        item {
            TextStr(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(start = 24.dp), text = "Ready-made hobbies"
            )
        }
        itemsIndexed(
            items = predefinedHabits,
            key = { _, habit: PredefinedHabit -> habit.habitName }
        ) { index, habit ->
            CreateHabitCard(
                icon = habit.icon,
                name = habit.habitName,
                onCardClick = { onAddHabit(habit) }
            )
        }
    }
}
@Composable
private fun TimePicker(
    selectedValue: HabitSchedule,
    selectedTime: LocalTime,
    onValueChange: (HabitSchedule, LocalTime) -> Unit,
    onCardClick: () -> Unit
){
    var hour by remember { mutableStateOf(selectedTime.hour) }
    var minute by remember { mutableStateOf(selectedTime.minute) }
    val tempTime = remember(hour, minute) {
        LocalTime.of(hour, minute)
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColumnPickerWheel(
                selectedValue = hour,
                onValueChange = {
                    hour = it
                },
                firstNumber = 0,
                lastNumber = 23
            )

            Spacer(Modifier.width(4.dp))

            Text(
                text = ":",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(Modifier.width(4.dp))

            ColumnPickerWheel(
                selectedValue = minute,
                onValueChange = {
                    minute = it
                },
                firstNumber = 0,
                lastNumber = 59
            )
        }
        Box(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(Black20)
        )
        SchedulePicker(
            selectedValue = selectedValue,
            onValueChange = {habitSchedule ->
                onValueChange(habitSchedule,tempTime)
            },
            onCardClick = onCardClick
        )
    }
}
@Composable
private fun ColumnPickerWheel(
    selectedValue: Int,
    onValueChange: (Int) -> Unit,
    firstNumber: Int,
    lastNumber: Int,
    modifier: Modifier = Modifier
) {
    val numbers = remember { (firstNumber..lastNumber).toList() }
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = numbers.indexOf(selectedValue).coerceAtLeast(0)
    )
    val snapBehavior = rememberSnapFlingBehavior(listState)

    LazyColumn(
        modifier = modifier
            .width(140.dp) // Фиксированная ширина для чисел
            .fillMaxHeight()
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .border(
                width = 1.dp,
                color = Black40,
                shape = RoundedCornerShape(4.dp)
            ),
        state = listState,
        flingBehavior = snapBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp),
        contentPadding = PaddingValues(vertical = 70.dp)
    ) {
        items(numbers) { number ->
            val isSelected = number == selectedValue

            Text(
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 24.dp)
                    .clickable {
                        onValueChange(number)
                    },
                text = number.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) Blue100 else Black40
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Black20)
            )
        }
    }
    // Следим за прокруткой и автоматически выбираем элемент в центре
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                numbers.getOrNull(index)?.let { onValueChange(it) }
            }
    }
}
@Composable
private fun SchedulePicker(
    selectedValue: HabitSchedule,
    onValueChange: (HabitSchedule) -> Unit,
    onCardClick: () -> Unit
) {
    var localSchedule by remember { mutableStateOf(selectedValue) }
    val selectedNumber = (localSchedule as? HabitSchedule.EveryNDays)?.n ?: 2
    val pinnedSetOfWeeks =
        ((localSchedule as? HabitSchedule.SpecificDays)?.daysOfWeek ?: emptySet()).toMutableSet()

    val onDayClick = { day: DayOfWeek ->
        val newSet = pinnedSetOfWeeks.toMutableSet()
        if (newSet.contains(day)) {
            newSet.remove(day)
        } else {
            newSet.add(day)
        }
        localSchedule = HabitSchedule.SpecificDays(newSet)
    }

    val onCardClickWithSave = {
        onValueChange(localSchedule)
        onCardClick()
    }
    //Every day
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Blue10)
                .clickable {
                    localSchedule = HabitSchedule.EveryDay
                    onCardClickWithSave()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Every Day",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        //Every N Days
        Row(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Blue10)
                .height(160.dp)
                .clickable {
                    onCardClickWithSave()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                text = "Every $selectedNumber Days",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            ColumnPickerWheel(
                selectedValue = selectedNumber,
                onValueChange = { number ->
                    localSchedule = HabitSchedule.EveryNDays(number)
                },
                firstNumber = 2,
                lastNumber = 31
            )
        }

        //SpecificDays
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Blue10)
                .clickable {
                    // Клик по всей карточке сохраняет выбор
                    onCardClickWithSave()
                }
        ) {
            // Заголовок
            Text(
                text = "Specific Days",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .padding(start = 16.dp),
                textAlign = TextAlign.Center
            )

            // Дни недели
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                DayOfWeek.entries.forEach { day ->
                    DayOfWeekItem(
                        modifier = Modifier
                            .weight(1f),
                        day = day,
                        isSelected = pinnedSetOfWeeks.contains(day),
                        onClick = { onDayClick(day) }
                    )
                }
            }
        }

    }
}
@Composable
private fun DayOfWeekItem(
    modifier: Modifier = Modifier,
    day: DayOfWeek,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) Blue100 else Color.Transparent,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Blue100 else Black40,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            style = MaterialTheme.typography.bodyLarge,
            color = if (isSelected) Color.White else Black60
        )
    }
}
@Composable
private fun UniversalAnimatedPicker(
    pickerType: PickerType,
    isVisible: Boolean = pickerType !is PickerType.Close,
    onDismissClick: () -> Unit,
    content: @Composable () -> Unit,
    title: String // Название пикера сверху
) {
    // Фон (отдельная анимация)
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f)
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable { onDismissClick() }
        )
    }

    // Карточка (отдельная анимация)
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(11f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .height(600.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(White100)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }) {}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                content()
            }
        }
    }
}

@Composable
private fun ColorPickerContent(
    colorGroups: List<ColorGroup>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        colorGroups.forEach { group ->

            // Заголовок группы на всю ширину
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black60,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Цвета группы
            items(group.colors) { color ->
                ColorItem(
                    color = color,
                    isSelected = color == selectedColor,
                    onClick = { onColorSelected(color) }
                )
            }
        }
    }
}

@Composable
fun ColorItem(
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) Black100 else Black20,
                shape = CircleShape
            )
            .clickable { onClick() }
    )
}

@Composable
private fun MetricPicker(
    habitMetric: HabitMetric,
    target: String,
    onTargetChanged: (HabitMetric, String) -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomTextFieldForPicker(
            modifier = Modifier
                .weight(0.5f),
            text = target,
            textPlace = "Enter target",
            onTextChanged = {
                Log.d("Metric", "target - $it")
                onTargetChanged(habitMetric, it)
            }
        )
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = habitMetric.formatWithQuantity(target),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "1 - ${habitMetric.getUnitForm("1")}"
            )
            Text(
                text = "50 - ${habitMetric.getUnitForm("50")}"
            )
        }
    }
    Box(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .padding(top = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Blue80)
            .padding(vertical = 12.dp)
            .clickable { onSaveClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Сохранить цель",
            color = Blue10,
            style = MaterialTheme.typography.bodySmall
        )
    }
    Box(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(Black20)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = 24.dp
        )
    ) {
        item {
            TextStr(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .padding(start = 24.dp),
                text = "Metrics"
            )
        }
        itemsIndexed(
            items = HabitMetric.entries,
            key = { _, metric: HabitMetric -> metric.displayName }
        ) { index, metric ->
            CreateHabitCard(
                onCardClick = { onTargetChanged(metric, target) },
                icon = metric.iconEmoji,
                name = metric.displayName,
            )
        }
    }
}


@Composable
private fun CreateHabitCard(
    icon: String,
    name: String,
    onCardClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Blue10)
            .border(
                width = 1.dp,
                color = Black40,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onCardClick() },
        verticalAlignment = Alignment.CenterVertically

    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .clip(CircleShape)
                .background(Blue20)
                .padding(horizontal = 12.dp)
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 16.sp
            )
        }

        Text(
            text = name
        )

    }
}

@Composable
private fun CustomTextFieldForPicker(
    modifier: Modifier = Modifier,
    text: String,
    textPlace: String,
    onTextChanged: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Black60,
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        value = text,
        onValueChange = onTextChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
            //Доделать цвета
            //focusedIndicatorColor = Color.Transparent,
            //unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.titleLarge,
        placeholder = {
            Text(
                modifier = Modifier,
                text = textPlace,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = Black60
            )
        }
    )
}
//@Composable
//private fun AnimatedPickerVisibility(
//    modifier: Modifier = Modifier,
//    isVisible: Boolean,
//    onDismissClick: () -> Unit,
//    onIconSelected: () -> Unit
//    ) {
//
//}

@Composable
private fun ReminderBlock(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    reminderTime: LocalTime,
    reminderDate: HabitSchedule,
    onSwitchClick: () -> Unit,
    onChoiceScheduleReminder: () -> Unit
) {
    Column(
        modifier = modifier
            .border(
                1.dp,
                Black10,
                RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 8.dp, end = 16.dp)
        ) {

            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Remember to set off time for a workout today",
                style = MaterialTheme.typography.bodyMedium,
                color = Black40
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = { onSwitchClick() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = GreenSuccess100,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Black20,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkBlue10)
                .clickable { onChoiceScheduleReminder() }
                .padding(vertical = 8.dp)
                .padding(start = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(com.example.hubtrackerapp.R.drawable.ic_clock),
                contentDescription = "arrow clock icon"
            )
            Text(text = reminderTime.toString())
            Image(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Default.Notifications,
                contentDescription = "arrow clock icon"
            )
            Text(text = reminderDate.toDisplayText())
        }
    }
}

@Composable
private fun ChoiceGoal(
    modifier: Modifier = Modifier,
    habitSchedule: String,
    metricForHabit: HabitMetric,
    target: String,
    onEditClick: () -> Unit,
    onChoiceSchedule: () -> Unit
) {
    Column(
        modifier = modifier
            .border(
                1.dp,
                Black10,
                RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 8.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = metricForHabit.formatWithQuantity(target),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "or more per day",
                    style = MaterialTheme.typography.bodySmall,
                    color = Black40
                )
            }
            EditBoxBtn(onEditClick = { onEditClick() })
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkBlue10)
                .clickable { onChoiceSchedule() }
                .padding(vertical = 8.dp)
                .padding(start = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(com.example.hubtrackerapp.R.drawable.ic_arrowclock),
                contentDescription = "arrow clock icon"
            )
            Text(text = "Daily")
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(com.example.hubtrackerapp.R.drawable.ic_paper),
                contentDescription = "arrow clock icon"
            )
            Text(text = habitSchedule)
        }
    }
}

@Composable
private fun EditBoxBtn(
    onEditClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Black10)
            .clickable {
                onEditClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = Icons.Default.Edit,
            contentDescription = "Edit Goal Metric and target value"
        )
    }
}

@Composable
private fun ChoiceIconAndColor(
    modifier: Modifier = Modifier,
    habitName: String,
    habitIcon: String,
    habitColor: Color,
    onIconClick: () -> Unit,
    onColorClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ChoiceParametersForHabit(
            modifier = Modifier
                .weight(1f),
            habitName = habitName,
            choiceName = "Icon",
            icon = habitIcon,
            onClick = { onIconClick() }
        )
        ChoiceParametersForHabit(
            modifier = Modifier
                .weight(1f),
            habitName = "None", //  подправить отображение названия цвета
            choiceName = "Color",
            color = habitColor,
            onClick = { onColorClick() }
        )
    }
}

@Composable
private fun ChoiceParametersForHabit(
    modifier: Modifier = Modifier,
    habitName: String,
    choiceName: String,
    icon: String = "None",
    color: Color = Blue10,
    onClick: () -> Unit
) {
    val choice = choiceName == "Icon"
    Row(
        modifier = modifier
            .border(
                1.dp,
                Black10,
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }, //click on open list icons or color
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp)
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            if (choice) {
                Text(
                    text = icon,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        Column(

        ) {
            if (habitName != "None") {
                Text(
                    text = habitName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = choiceName,
                style = MaterialTheme.typography.bodySmall,
                color = Black40
            )
        }
    }
}

@Composable
private fun TextStr(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelSmall
    )
}

@Composable
private fun TextContent(
    modifier: Modifier = Modifier,
    text: String,
    textPlace: String,
    onTextChanged: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onTextChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
            //Доделать цвета
            //focusedIndicatorColor = Color.Transparent,
            //unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.titleLarge,
        placeholder = {
            Text(
                text = textPlace,
                style = MaterialTheme.typography.titleLarge,
                color = Black20
            )
        }
    )
}