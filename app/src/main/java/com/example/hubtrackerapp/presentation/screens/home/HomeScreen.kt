@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.home

import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hubtrackerapp.R
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.domain.hubbit.models.forUi.Mood
import com.example.hubtrackerapp.presentation.navigation.BottomTab
import com.example.hubtrackerapp.presentation.screens.components.ModSwitcher
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black100
import com.example.hubtrackerapp.presentation.theme.Black20
import com.example.hubtrackerapp.presentation.theme.Black40
import com.example.hubtrackerapp.presentation.theme.Black60
import com.example.hubtrackerapp.presentation.theme.Blue10
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.Blue40
import com.example.hubtrackerapp.presentation.theme.GreenSuccess100
import com.example.hubtrackerapp.presentation.theme.White100
import java.time.LocalDate

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        //  HomeViewModel()
    )
) {
    val state by viewModel.state.collectAsState()
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
                TopActionRow()
                ProfileRow()
                ModSwitcher(
                    selected = state.mode,
                    onModChange = viewModel::changeMode
                )

                //gray line
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
                        bottom = 60.dp
                    )// глянуть потом насколько он тут вообще нужон
                ) {
                    //Date Boxes Row
                    item {
                        CalendarRow(
                            dates = state.calendarDays,
                            selectedDate = state.selectedDate,
                            onDateClick = {
                                viewModel.processCommand(
                                    HabitCommands.ChangeDate(it)
                                )
                            }
                        )
                    }
                    item {
                        StatisticBox(
                            onClick = {
                                Log.d("HomeScreen", "onStaticBoxClick")
                            },
                            progress = state.completedCount,
                            allHabitsCount = state.habits.size
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
                        items = state.habits,
                        key = { _, habit: HabitWithProgressUi -> habit.habitId }
                    ) { index, habits ->

                        HabitCard(
                            habits,
                            onPlusBoxClick = {
                                viewModel.processCommand(HabitCommands.SwitchCompletedStatus(it))
                            }
                        )
                    }
                }
            }
            CustomBottomBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(0f)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                selected = BottomTab.HOME,
                onTabClick = { viewModel.onEvent(HomeEvent.AddClicked) } //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            )

            AnimatedVisibility(
                visible = state.addMenuVisible,
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
                            interactionSource = remember{ MutableInteractionSource() }
                        ){
                            viewModel.onEvent(HomeEvent.DismissAddMenu)
                        }
                )
            }
            AnimatedVisibility(
                modifier = Modifier
                    .zIndex(3f)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 76.dp),
                visible = state.addMenuVisible,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                AddMenu()
            }

        }
    }
}

@Composable
fun AddMenu(

){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            //.background(Color.Transparent)
            .padding(16.dp)
    ) {
        Row() {
            AddHabitCard("Quit Bad Habit","Never too late", Icons.Default.Done)
            Spacer(Modifier.height(8.dp))
            AddHabitCard("Quit Bad Habit","Never too late", Icons.Default.Close)
            Spacer(Modifier.height(12.dp))
        }

        AddMoodRow()
    }
}
@Composable
fun AddMoodRow(

){
    Row {
        TextForChallengesAndHabits(
            cardName = "Add Mood",
            additionalInfo = "How're you feeling?"
        )
        Mood.entries.forEach { mood->
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
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
    textHabitFirst: String,
    textHabitSecond: String,
    imageVector: ImageVector
){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(White100)
            .padding(16.dp)

    ) {
        Column() {
            Text(
                text = textHabitFirst
            )
            Text(
                text = textHabitSecond
            )
        }
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
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
fun HabitCard(
    habitWithProgress: HabitWithProgressUi,
    onPlusBoxClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 12.dp)
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
            additionalInfo = "daysLeft"
        )
        ParticipantsRow(fakeParticipants(), size = 28.dp)
        AddAndCompleteHabit(
            habitId = habitWithProgress.habitId,
            habitIsCompleted = habitWithProgress.isCompleted,
            onPlusBoxClick = onPlusBoxClick
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
fun TopActionRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                TODO("Calendar button")
            }
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(1.dp, Black10, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Icon(
                    Icons.Default.DateRange,
                    contentDescription = "Calendar button"
                )
            }


        }

        IconButton(
            onClick = {
                TODO("Notifications Button")
            }
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(1.dp, Black10, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = "Notifications Button"
                )
            }
        }
    }
}

@Composable
fun ProfileRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(
                text = "Hi, Mert",
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
                    TODO("EMOJI BUTTON")
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
    //УБРАТЬ ЛОГИКУ В VIEWMODEL!!!
    val colorInBox = if (isSelected) Blue100 else Black20
    val colorInBoxNumbers = if (isSelected) Blue100 else Black100
    val borderWidth = if (isSelected) 2.dp else 1.dp
    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 64.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = borderWidth,
                color = colorInBox,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick(day.date) }, // выбор дня в списке
        contentAlignment = Alignment.Center
    ) {
        Column(
            //verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.dayNumber.toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = colorInBoxNumbers
            )
            Text(
                text = day.dayOfWeek,
                style = MaterialTheme.typography.labelSmall,
                color = colorInBox
            )
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
            ProgressCircle(progress = (progress / 100).toFloat())
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Your daily goals almost done!",
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
            style = MaterialTheme.typography.bodyLarge,
            color = White100
        )
    }
}