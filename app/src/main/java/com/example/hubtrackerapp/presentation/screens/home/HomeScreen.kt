@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hubtrackerapp.domain.hubbit.models.forUi.CalendarDayUi
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black100
import com.example.hubtrackerapp.presentation.theme.Black20
import com.example.hubtrackerapp.presentation.theme.Black40
import com.example.hubtrackerapp.presentation.theme.Black60
import com.example.hubtrackerapp.presentation.theme.Blue10
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.Blue40
import com.example.hubtrackerapp.presentation.theme.White100
import java.time.LocalDate

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel {
        HomeViewModel
    }
) {
    val mode by viewModel.mode.collectAsState()
    val daysInMonth by viewModel.calendarDays.collectAsState()
    Scaffold(
        modifier = modifier
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            TopActionRow()
            ProfileRow()
            ModSwitcher(
                selected = mode,
                onModChange = viewModel::changeMode
            )


            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Black20)
            )

            LazyColumn(
                modifier = Modifier
            ) {
                //Date Boxes Row
                item {
                    CalendarRow(
                        dates = daysInMonth,
                        onDateClick = {}
                    )
                }
                item {
                    StatisticBox(
                        onClick = {},
                        progress = 0.25f
                    )
                }
            }
        }
    }
}

@Composable
fun TopActionRow() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                TODO("Calendar button")
            }
        ) {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Calendar button"
            )
        }

        IconButton(
            onClick = {
                TODO("Notifications Button")
            }
        ) {
            Icon(
                Icons.Default.Notifications,
                contentDescription = "Notifications Button"
            )
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


@Composable
private fun ModSwitcher(
    selected: ModeForSwitch,
    onModChange: (ModeForSwitch) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Black10)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ModeItem(
            modifier = Modifier.weight(1f),
            text = "Today",
            selected = selected == ModeForSwitch.HOBBIES,
            onClick = { onModChange(ModeForSwitch.HOBBIES) }
        )
        ModeItem(
            modifier = Modifier.weight(1f),
            text = "Clubs",
            selected = selected == ModeForSwitch.CLUBS,
            onClick = { onModChange(ModeForSwitch.CLUBS) }
        )

    }
}


@Composable
private fun ModeItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(3.dp)
            .background(if (selected) White100 else Color.Transparent,RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .padding(5.dp),

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Blue100 else Black60
        )
    }
}
// Habbits screen composables


@Composable
fun CalendarRow(
    dates: List<CalendarDayUi>,
    //selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            items = dates,
            key = { it.dayNumber }
        ) { day ->
            DateCard(
                day = day,
                onClick = onDateClick
            )
        }
    }
}

@Composable
fun DateCard(
    day: CalendarDayUi,
    onClick: (LocalDate) -> Unit
) {
    //УБРАТЬ ЛОГИКУ В VIEWMODEL!!!
    val colorInBox = if (day.isToday) Blue100 else Black20
    val colorInBoxNumbers = if (day.isToday) Blue100 else Black100
    val borderWidth = if (day.isToday) 2.dp else 1.dp
    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 64.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(day.date) } // выбор дня в списке
            .border(
                width = borderWidth,
                color = colorInBox,
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (day.dayNumber).toString(),
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
    progress: Float
    //передавать количество выполненных habbits
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp)
            .background(Blue100,
                shape = RoundedCornerShape(16.dp))




    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressCircle(progress = progress)
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
                    text = "1 of 4 completed",
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
    strokeWidth: Dp = 3.dp
){
    Box(
        modifier = modifier.size(50.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
                .padding(2.dp)
        ) {
            val stroke = Stroke(
                width = strokeWidth.toPx(),
                cap = StrokeCap.Round
            )

            drawArc(
                color = Blue40,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )

            drawArc(
                color = White100,
                startAngle = -90f,
                sweepAngle = 360f * progress,
                useCenter = false,
                style = stroke
            )
        }
        Text(
            text = "%${(progress * 100).toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = White100
        )
    }
}