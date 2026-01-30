@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.home

import HabitMetric
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.presentation.theme.*
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.hubtrackerapp.R
import com.example.hubtrackerapp.domain.hubbit.models.HomeMenuType
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.hubbit.models.SwipeHabitState
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.hubbit.models.forUi.Mood
import com.example.hubtrackerapp.presentation.navigation.BottomTab
import com.example.hubtrackerapp.presentation.screens.components.ModSwitcher
import com.example.hubtrackerapp.presentation.screens.components.SwitcherOption
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onAddHabitClick: (String?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val predefinedHabits = viewModel.predefinedHabits
//    val mode by viewModel.mode.collectAsState()
//    val daysInMonth by viewModel.calendarDays.collectAsState()
//    val selectedDate by viewModel.selectedDate.collectAsState()
    Scaffold(
        modifier = modifier
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                TopActionRow(
                    onCalendarButtonClick = {
                        viewModel.onEvent(HomeEvent.OpenPicker(HomePickerType.Calendar))
                    },
                    onNotificationsButtonClick = {
                        viewModel.onEvent(HomeEvent.OpenPicker(HomePickerType.Notifications))
                    }
                )
                ProfileRow(
                    userName = state.userName,
                    onEmojiButtonClick = {
                        viewModel.onEvent(HomeEvent.OpenPicker(HomePickerType.EmojiBar))
                    }
                )
                ModSwitcher(
                    selected = state.mode,
                    onModChange = {
                        viewModel.onEvent(HomeEvent.ChangeModeScreen(it))
                    },
                    options = listOf(
                        SwitcherOption("Hobbies", ModeForSwitch.HOBBIES),
                        SwitcherOption("Clubs", ModeForSwitch.CLUBS)
                    )
                )
                //gray line
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Black20)
                )
                when (state.mode) {
                    ModeForSwitch.HOBBIES -> {
                        HomeTodayContent(
                            datesList = state.calendarDays,
                            selectedDate = state.selectedDate,
                            onDateClick = {
                                viewModel.onEvent(HomeEvent.OnDateChanged(it))
                                //                               viewModel.processCommand(
//                        HabitCommands.ChangeDate(it)
//                    )
                            },
                            completedCount = state.completedCount,
                            allHabitsCount = state.habits.size,
                            habitsList = state.habits,
                            onPlusBoxClick = {
                                viewModel.processCommand(HabitCommands.OpenEditor(it))
                                //  viewModel.processCommand(HabitCommands.SwitchCompletedStatus(it))
                                //  viewModel.processCommand(HabitCommands.SwitchCompletedStatus(it))
                            },
                            editProgressState = state.editProgressState,
                            canEditToday = state.canEditToday,
                            onSwipe = viewModel::processCommand,
                            onClick = viewModel::processCommand,
                            onEditHabit = {
                                onAddHabitClick(it)
                            }
                        )
                    }

                    ModeForSwitch.CLUBS -> {}
                }

            }
            CustomBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(0f)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                selected = BottomTab.HOME,
                onTabClick = { tab ->
                    //В последствии исправить навигацию мб вынести в общую ViewModel для BottomBar логику

                    viewModel.onEvent(HomeEvent.AddClicked)
                } //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            )

            AnimatedVisibility(
                visible = state.activeHomeMenu == HomeMenuType.ADD_MENU,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(2f)
                        .background(Black100.copy(alpha = 0.4f))
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            viewModel.onEvent(HomeEvent.DismissAddMenu)
                        }
                )
            }
            //СДЕЛАТЬ ЧТОБ ОНА ВЫЗЫВАЛА ИЛИ добавление готового хобби в профиль
            //либо если кликнули на бед хабит или гуд хабит вызывался Ботом Щит
            //а после viewModel.onEvent(HomeEvent.DismissAddMenu)
            AnimatedVisibility(
                modifier = Modifier
                    .zIndex(3f)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 76.dp),
                visible = state.activeHomeMenu == HomeMenuType.ADD_MENU,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                AddMenu(
                    onClick = {
                        viewModel.onEvent(HomeEvent.OpenBottomSheet)
                        //onAddHabitClick()
                    }
                )
            }
            // Сделать еще одно состояние в HomeEvent чтоб только щит открывать и через него тыкать
            val sheetState = rememberModalBottomSheetState()
            if (state.activeHomeMenu == HomeMenuType.BOTTOM_SHEET) {
                ModalBottomSheet(
                    onDismissRequest = {
                        viewModel.onEvent(HomeEvent.DismissAddMenu)
                    },
                    sheetState = sheetState,
                    containerColor = Color.White,
                    tonalElevation = 8.dp,
                    dragHandle = { BottomSheetDefaults.DragHandle() }
                ) {
                    AddOrCreateHabit(
                        predefinedHabits = predefinedHabits,
                        onAddHabitClick = {
                            viewModel.onEvent(HomeEvent.DismissAddMenu)
                            onAddHabitClick(it)
                        }, // создание/изменение нового/старого хобби
                    )
//                    AddMenu(
//                        onClick = {
//                            viewModel.onEvent(HomeEvent.DismissAddMenu)
//                            onAddHabitClick()
//                        }
//                    )
                }
            }
        }
    }
}

@Composable
private fun AddOrCreateHabit(
    predefinedHabits: List<PredefinedHabit>,
    onAddHabitClick: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "New Habit",
            style = MaterialTheme.typography.labelSmall,
            color = Black40
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Black10,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 12.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Create Custom Habit",
                style = MaterialTheme.typography.bodyMedium
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .border(
                        width = 1.dp,
                        color = Black10,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        onAddHabitClick(null) // Создние нового хобби
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add New Habit"
                )
            }
        }
        Text(
            text = "Popular Habits",
            style = MaterialTheme.typography.labelSmall,
            color = Black40
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(
                bottom = 10.dp
            )
        ) {
            itemsIndexed(
                items = predefinedHabits,
                key = { _, habit: PredefinedHabit -> habit.habitName }
            ) { index, habit ->
                PredefinedHabitCard(
                    habit = habit,
                    onPredefinedHabitClick = {
                        onAddHabitClick(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun PredefinedHabitCard(
    habit: PredefinedHabit,
    onPredefinedHabitClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .size(width = 128.dp, height = 102.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(habit.color)
            .clickable {
                onPredefinedHabitClick(habit.habitName)
            }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(White100)
                .padding(horizontal = 4.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = habit.icon
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .weight(1f),
            text = habit.habitName,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = habit.metricForHabit.formatWithQuantity(habit.target),
            color = Black60,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun HomeTodayContent(
    datesList: List<CalendarDayUi>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    completedCount: Int,
    allHabitsCount: Int, //state.habits.size
    editProgressState: EditProgressState?,
    habitsList: List<HabitWithProgressUi>,
    canEditToday: Boolean,
    onPlusBoxClick: (String) -> Unit,
    onSwipe: (HabitCommands) -> Unit,
    onClick: (HabitCommands) -> Unit,
    onEditHabit: (String) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = 60.dp
        )
    ) {
        //Date Boxes Row
        item {
            CalendarRow(
                dates = datesList,
                selectedDate = selectedDate,
                onDateClick = {
                    onDateClick(it)
//                    viewModel.processCommand(
//                        HabitCommands.ChangeDate(it)
//                    )
                }
            )
        }
        item {
            StatisticBox(
                onClick = {
                    Log.d("HomeScreen", "onStaticBoxClick")
                },
                progress = completedCount,
                allHabitsCount = allHabitsCount
            )
        }
        item {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp),
                text = "Challenges",
                style = MaterialTheme.typography.bodyMedium
            )
            //переписать добавить кнопку ViewAll если будет надобность для обзора всех челенджей юзера
        }
        item {
            ChallengesRow("Best Runners!", "5 days 13 hours left")
        }
        item {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp),
                text = "Habits",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        itemsIndexed(
            items = habitsList,
            key = { _, habit: HabitWithProgressUi -> habit.habitId }
        ) { index, habits ->


            SwipeHabitItem(
                habit = habits,
                onSwipe = { onSwipe(HabitCommands.OnHabitSwiped(habits.habitId, it)) },
                leftMenu = {
                    LeftMenu(
                        habits.habitId,
                        onClick = { onClick(it) },
                        canEditToday = canEditToday,
                        isCompleted = habits.isCompleted,
                        onEditHabit = onEditHabit
                    )
                },
                rightMenu = {
                    RightMenu(
                        habits.habitId,
                        onClick = { onClick(it) },
                        canEditToday = canEditToday
                    )
                }
            ) {
                if (habits.isInTargetMode && editProgressState != null) {
                    EditProgressPanel(
                        habitMetric = habits.metric,
                        editState = editProgressState,
                        onCancel = { onClick(HabitCommands.CancelEditor) },
                        onApply = { onClick(HabitCommands.ApplyEditor) },
                        onValueChange = { onClick(HabitCommands.UpdateEditor(it)) }
                    )
                } else {
                    HabitCard(
                        habits,
                        onPlusBoxClick = {
                            onPlusBoxClick(it)
                            //  viewModel.processCommand(HabitCommands.SwitchCompletedStatus(it))
                        },
                        canEditToday = canEditToday
                    )
                }
            }
        }
    }
}

@Composable
fun CompactNumberField(
    value: String,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(36.dp)
            .border(
                width = 1.dp,
                color = Black40,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(
                color = Black100,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun EditProgressPanel(
    editState: EditProgressState,
    onCancel: () -> Unit,
    onApply: () -> Unit,
    onValueChange: (String) -> Unit,
    habitMetric: HabitMetric
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = Black10,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 4.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {

        // CANCEL
        IconButton(
            modifier = Modifier.size(36.dp),
            onClick = onCancel
        ) {
            Icon(Icons.Default.Close, null)
        }
        Box(
            Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Black10)
        )
        // MAIN CONTENT
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            // progress text
            Text(
                text = "${editState.tempProgress} / ${editState.target} ${
                    habitMetric.getUnitForm(
                        editState.target.toString()
                    )
                }",
                style = MaterialTheme.typography.bodySmall,
                color = Black40
            )

            // divider
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Black20)
            )

            // input
            CompactNumberField(
                value = editState.tempProgressText,
                onValueChange = { text ->
                    if (text.all { it.isDigit() }) {
                        onValueChange(text)
                    }
                }
            )
        }
        Box(
            Modifier
                .fillMaxHeight()
                .width(1.dp)
                .background(Black10)
        )
        // progress circle
        ProgressCircle(
            progress = editState.percent,
            size = 50.dp,
            strokeWidth = 2.dp,
            colorBackground = Black10,
            colorCompleted = Blue100,
            colorText = Black60
        )

        // PLUS
        Box(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f)
                .size(height = 40.dp, width = 25.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = Black10,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    val step = (editState.target * 0.05f)
                        .toInt()
                        .coerceAtLeast(1)

                    onValueChange((editState.tempProgress + step).toString())
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.KeyboardArrowUp, null)
        }

        // MINUS
        Box(
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(0.5f)
                .size(height = 40.dp, width = 25.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = Black10,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    val step = (editState.target * 0.05f)
                        .toInt()
                        .coerceAtLeast(1)

                    onValueChange((editState.tempProgress - step).toString())
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.KeyboardArrowDown, null)
        }


        Box(
            Modifier
                .padding(start = 3.dp)
                .fillMaxHeight()
                .width(1.dp)
                .background(Black10)
        )
        // APPLY
        IconButton(
            modifier = Modifier.size(36.dp),
            onClick = onApply
        ) {
            Icon(
                Icons.Default.Done,
                null,
                tint = GreenSuccess100
            )
        }
    }
}


@Composable
fun RightMenu(
    habitId: String,
    canEditToday: Boolean,
    onClick: (HabitCommands) -> Unit
) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(White100)
            .border(
                width = 1.dp,
                color = Black10,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (canEditToday) {
                        onClick(HabitCommands.FailHabitInThisDay(habitId))
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Close, null, tint = Color.Red)
            Text(
                text = "Fail",
                style = MaterialTheme.typography.bodySmall,
                color = Black40
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 16.dp)
                .background(Black10)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (canEditToday) {
                        onClick(HabitCommands.SkipHabitInThisDay(habitId))
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.KeyboardArrowRight, null/*, tint = Color.White*/)
            Text(
                text = "Skip",
                style = MaterialTheme.typography.bodySmall,
                color = Black40
            )
        }
    }
}

@Composable
fun LeftMenu(
    habitId: String,
    canEditToday: Boolean,
    isCompleted: Boolean,
    onClick: (HabitCommands) -> Unit,
    onEditHabit: (String) -> Unit,
    text: String = if (!isCompleted) "Done" else "UnDone",
    iconDone: ImageVector = if (!isCompleted) Icons.Default.Done else Icons.Default.Close
) {
    val x = Icons.Default.Done
    Row(
        modifier = Modifier
            .height(80.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(White100)
            .border(
                width = 1.dp,
                color = Black10,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (canEditToday) {
                        onEditHabit(habitId)

                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Edit, null/*, tint = Color.White*/)
            Text(
                text = "View",
                style = MaterialTheme.typography.bodySmall,
                color = Black40
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
                .padding(vertical = 16.dp)
                .background(Black10)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    if (canEditToday) {
                        onClick(HabitCommands.SwitchCompletedStatus(habitId))
                    }
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(iconDone, null, tint = GreenSuccess100)
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = Black40
            )
        }
    }
//    IconButton(onClick = {
//        onClick(HabitCommands.SkipHabitInThisDay(habitId))
//    }) {
//        Icon(Icons.Default.Close, null, tint = Color.White)
//    }
//
//    IconButton(onClick = {
//            HabitCommands.FailHabitInThisDay(habitId)
//    }) {
//        Icon(Icons.Default.Notifications, null, tint = Color.White)
//    }
}

@Composable
fun AddMenu(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AddHabitCard(
                modifier = Modifier
                    .weight(1f),
                "Quit Bad Habit",
                "Never too late",
                Icons.Default.Close,
                onClick = { onClick() }
            )
            AddHabitCard(
                modifier = Modifier
                    .weight(1f),
                "New Good Habit",
                "For a better life",
                Icons.Default.Done,
                onClick = { onClick() }
            )

        }
        Spacer(Modifier.height(12.dp))
        AddMoodRow()
    }
}

@Composable
fun AddMoodRow(

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White100)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        TextForChallengesAndHabits(
            cardName = "Add Mood",
            additionalInfo = "How're you feeling?"
        )

        Mood.entries.forEach { mood ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = Black10,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF5F5F5))
                    .clickable { /*onMoodSelected(mood)*/ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = mood.emoji,
                    fontSize = 22.sp
                )
            }
        }
    }

}

@Composable
fun AddHabitCard(
    modifier: Modifier = Modifier,
    textHabitFirst: String,
    textHabitSecond: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(White100)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = textHabitFirst
            )
            Text(
                text = textHabitSecond
            )
        }
        Box(
            modifier = Modifier
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        }
    }
}

@Composable
fun CustomBottomBar(
    modifier: Modifier = Modifier,
    selected: BottomTab,
    onTabClick: (BottomTab) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .border(
                1.dp,
                Black10,
                RoundedCornerShape(32.dp)
            )
            .background(White100),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomBarItem(
            icon = Icons.Default.Home,
            selected = selected == BottomTab.HOME,
            onClick = { onTabClick(BottomTab.HOME) }
        )
        BottomBarItem(
            icon = Icons.Default.Search,
            selected = selected == BottomTab.EXPLORE,
            onClick = { onTabClick(BottomTab.EXPLORE) }
        )
        BottomBarItem(
            icon = Icons.Default.AddCircle,
            selected = selected == BottomTab.CREATE,
            isCenter = true,
            onClick = { onTabClick(BottomTab.CREATE) }
        )
        BottomBarItem(
            icon = Icons.Default.Star,
            selected = selected == BottomTab.ACTIVITY,
            onClick = { onTabClick(BottomTab.ACTIVITY) }
        )
        BottomBarItem(
            icon = Icons.Default.AccountCircle,
            selected = selected == BottomTab.PROFILE,
            onClick = { onTabClick(BottomTab.PROFILE) }
        )
    }
}

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    selected: Boolean,
    isCenter: Boolean = false,
    onClick: () -> Unit
) {
    val size = if (isCenter) 48.dp else 36.dp
    val bg = Color.Transparent
    val tint = if (selected || isCenter) Blue100 else Black40
    val iconSize = if (isCenter) 48.dp else 24.dp
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(bg)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize),
            imageVector = icon,
            contentDescription = null,
            tint = tint
        )
    }
}

@Composable
fun SwipeHabitItem(
    habit: HabitWithProgressUi,
    onSwipe: (SwipeHabitState) -> Unit,
    leftMenu: @Composable () -> Unit,
    rightMenu: @Composable () -> Unit,
    content: @Composable () -> Unit
) {


    //val maxOffset = 270f
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    var menuWidthPx by remember { mutableStateOf(0f) }
    val maxOffset = menuWidthPx

    LaunchedEffect(habit.swipeState) {
        when (habit.swipeState) {
            SwipeHabitState.CLOSED -> offsetX.animateTo(0f)
            SwipeHabitState.OPEN_LEFT -> offsetX.animateTo(maxOffset)
            SwipeHabitState.OPEN_RIGHT -> offsetX.animateTo(-maxOffset)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 4.dp)
            .height(80.dp)
    ) {

        Box(
            modifier = Modifier
                .matchParentSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier.onSizeChanged {
                    menuWidthPx = it.width.toFloat()
                }
            ) {
                leftMenu()
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier.onSizeChanged {
                    menuWidthPx = it.width.toFloat()
                }
            ) {
                rightMenu()
            }
        }

        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.value.toInt(), 0) }
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
                .background(White100)// Исправить цвет
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                val newState = when {
                                    offsetX.value > maxOffset / 2 -> SwipeHabitState.OPEN_LEFT
                                    offsetX.value < -maxOffset / 2 -> SwipeHabitState.OPEN_RIGHT
                                    else -> SwipeHabitState.CLOSED
                                }
                                onSwipe(newState)
                                val targetOffset = when (newState) {
                                    SwipeHabitState.CLOSED -> 0f
                                    SwipeHabitState.OPEN_LEFT -> maxOffset
                                    SwipeHabitState.OPEN_RIGHT -> -maxOffset
                                }
                                offsetX.animateTo(targetOffset)
                            }
                        }
                    ) { _, dragAmount ->
                        scope.launch {
                            offsetX.snapTo(
                                (offsetX.value + dragAmount)
                                    .coerceIn(-maxOffset, maxOffset)
                            )
                        }
                    }
                }
        ) {
            content()
        }
    }
}

@Composable
fun HabitCard(
    habitWithProgress: HabitWithProgressUi,
    canEditToday: Boolean,
    onPlusBoxClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            //.fillMaxWidth()
            //.padding(horizontal = 24.dp)
            //.padding(top = 12.dp)
            .border(
                width = 1.dp,
                color = Black10,
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProgressCircle(
            modifier = Modifier
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
            progress = habitWithProgress.progress,
            colorBackground = Black10,
            colorCompleted = Blue100,
            size = 40.dp,
            strokeWidth = 2.dp,
            text = habitWithProgress.emoji
        )
        TextForChallengesAndHabits(
            modifier = Modifier
                .weight(1f),
            cardName = habitWithProgress.title,
            additionalInfo = "${habitWithProgress.progressWithTarget} / ${habitWithProgress.target} ${
                habitWithProgress.metric.getUnitForm(
                    habitWithProgress.target
                )
            }"
        )
        ParticipantsRow(fakeParticipants(), size = 28.dp)
        AddAndCompleteHabit(
            habitId = habitWithProgress.habitId,
            habitIsCompleted = habitWithProgress.isCompleted,
            onPlusBoxClick = {
                if (canEditToday) {
                    onPlusBoxClick(it)
                }
            }
        )
    }
}

@Composable
fun AddAndCompleteHabit(
    habitId: String,
    habitIsCompleted: Boolean,
    onPlusBoxClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(end = 12.dp)
            .size(36.dp)
            .border(
                width = 1.dp,
                color = Black10,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = !habitIsCompleted, onClick = { onPlusBoxClick(habitId) }),
//            .clickable{
//
//                TODO("ДОБАВИТЬ ЛОГИКУ КНОПКУ ДОБАВЛЕНИЯ К ХОББИ ИЛИ ЕГО ЗАВЕРШЕНИЕ!")
//                onClick()
//            },
        contentAlignment = Alignment.Center
    ) {
        if (!habitIsCompleted) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add to habit"
            )
        } else {
            Icon(
                Icons.Default.Done,
                contentDescription = "Add to habit",
                tint = GreenSuccess100
            )
        }
    }
}

@Composable
fun ChallengesRow(
    challengeName: String,
    daysLeft: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 12.dp)
            .border(
                width = 1.dp,
                color = Black10,
                shape = RoundedCornerShape(16.dp)
            )
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_clock),
                contentDescription = "Clock icon"
            )
            TextForChallengesAndHabits(
                modifier = Modifier
                    .weight(1f),
                cardName = challengeName,
                additionalInfo = daysLeft
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                val participants = fakeParticipants()
                ParticipantsRow(participants)
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "${participants.size} friends joined",
                    style = MaterialTheme.typography.bodySmall,
                    color = Black40
                )
            }
        }
        CustomLinearProgressIndicator(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp),
            progress = 0.5f
        )

    }
}

@Composable
fun TextForChallengesAndHabits(
    modifier: Modifier = Modifier,
    cardName: String,
    additionalInfo: String
) {

    Column(
        modifier = modifier
            .padding(start = 8.dp)

    ) {
        Text(
            text = cardName,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = additionalInfo,
            style = MaterialTheme.typography.bodySmall,
            color = Black40
        )
    }
}

@Composable
fun ParticipantsRow(
    avatars: List<ImageBitmap>,
    size: Dp = 20.dp
) {
    Row {
        avatars.take(3).forEachIndexed { index, avatar ->

            if (index < 2) {
                AvatarImage(index, avatar, size)
            } else {
                Box(
                    modifier = Modifier
                        .size(size)
                        .offset(x = (-index * 8).dp)
                        .clip(CircleShape)
                        .background(Blue10),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+${avatars.size - 2}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Blue100
                    )
                }
            }
        }
    }
}

///////////////////////////////////


@Composable
fun AvatarImage(
    index: Int,
    avatar: ImageBitmap,
    size: Dp = 20.dp
) {
    Image(
        modifier = Modifier
            .size(size)
            .offset(x = (-index * 8).dp)
            .clip(CircleShape)
            .border(1.dp, Color.White, CircleShape),
        bitmap = avatar,
        contentDescription = "avatar"
    )
}

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: Float,
    height: Dp = 4.dp,
    backgroundColor: Color = Black40,
    progressColor: Color = Blue100
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .background(progressColor, RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun TopActionRow(
    onCalendarButtonClick: () -> Unit,
    onNotificationsButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .border(1.dp, Black10, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onCalendarButtonClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Calendar button"
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .border(1.dp, Black10, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onNotificationsButtonClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications Button"
            )
        }
    }
}

@Composable
fun ProfileRow(
    userName: String,
    onEmojiButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = "Hi,$userName \uD83D\uDC4B\uD83C\uDFFB",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Let's make habits together!",
                style = MaterialTheme.typography.bodyLarge,
                color = Black40
            )
        }
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Blue10)
                .clickable {
                    onEmojiButtonClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "\uD83D\uDE07",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}


// Habbits screen composables


@Composable
fun CalendarRow(
    dates: List<CalendarDayUi>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit
) {
    val listState = rememberLazyListState()
    LaunchedEffect(dates) {
        val todayIndex = dates.indexOfFirst { it.isToday }
        if (todayIndex != -1) {
            listState.scrollToItem(todayIndex)
        }
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            items = dates,
            key = { it.date }
        ) { day ->
            DateCard(
                day = day,
                isSelected = day.date == selectedDate,
                onClick = onDateClick
            )
        }
    }
}

@Composable
fun DateCard(
    day: CalendarDayUi,
    isSelected: Boolean,
    onClick: (LocalDate) -> Unit
) {

    val borderColor = when {
        day.isToday && isSelected -> GreenSuccess100 // сегодня + выбран
        day.isToday -> GreenSuccess100.copy(alpha = 0.7f) // только сегодня
        isSelected -> Blue100 // только выбран
        else -> Black20 // обычный
    }

    val numberColor = when {
        day.isToday && isSelected -> GreenSuccess100
        day.isToday -> GreenSuccess100
        isSelected -> Blue100
        else -> Black100
    }

    val dayOfWeekColor = when {
        day.isToday && isSelected -> GreenSuccess100.copy(alpha = 0.8f)
        day.isToday -> GreenSuccess100.copy(alpha = 0.6f)
        isSelected -> Blue100.copy(alpha = 0.6f)
        else -> Black40
    }

    val borderWidth = when {
        day.isToday && isSelected -> 2.dp
        day.isToday || isSelected -> 1.5.dp
        else -> 1.dp
    }
    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 64.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(day.date) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Номер дня
            Text(
                text = day.dayNumber.toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = numberColor
            )

            // День недели
            Text(
                text = day.dayOfWeek,
                style = MaterialTheme.typography.labelSmall,
                color = dayOfWeekColor
            )

            // Индикатор "сегодня" под днем недели
            if (day.isToday) {
                Spacer(modifier = Modifier.height(2.dp))
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(GreenSuccess100)
                )
            }
        }
    }
}

@Composable
fun StatisticBox(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    progress: Int,
    allHabitsCount: Int
    //передавать количество выполненных habbits
) {

    val progressPercentage = if (allHabitsCount > 0) {
        progress.toFloat() / allHabitsCount.toFloat()
    } else {
        0f
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp)
            .background(
                Blue100,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressCircle(progress = progressPercentage)
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = if(progress == allHabitsCount) {
                        "Your daily goals done!"
                    } else {
                        "Your daily goals almost done!"
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = White100
                )
                Text(
                    text = "$progress of $allHabitsCount completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = Blue40
                )
            }
        }

    }

}

@Composable
fun ProgressCircle(
    modifier: Modifier = Modifier,
    progress: Float,
    strokeWidth: Dp = 3.dp,
    size: Dp = 50.dp,
    colorBackground: Color = Blue40,
    colorCompleted: Color = White100,
    colorText: Color = White100,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    text: String = "%${(progress * 100).toInt()}"
) {

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            val stroke = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )

            drawArc(
                color = colorBackground,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            drawArc(
                color = colorCompleted,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = stroke
            )
        }
        Text(
            text = text,
            style = textStyle,
            color = colorText
        )
    }
}