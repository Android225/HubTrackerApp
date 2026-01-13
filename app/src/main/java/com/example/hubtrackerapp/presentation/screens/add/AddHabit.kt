@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.add

import HabitMetric
import android.R
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.presentation.screens.components.ModSwitcher
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationViewModel
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black20
import com.example.hubtrackerapp.presentation.theme.Black40
import com.example.hubtrackerapp.presentation.theme.Black60
import com.example.hubtrackerapp.presentation.theme.Blue10
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.DarkBlue10
import com.example.hubtrackerapp.presentation.theme.GreenSuccess100
import com.example.hubtrackerapp.presentation.theme.Orange100
import com.example.hubtrackerapp.presentation.theme.White100
import java.time.LocalTime


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
    val predifinedHabits = viewModel.predifinedHabits
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
                                .background(White100),
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
                    .padding(bottom = 24.dp, top = 16.dp)
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
                habitColor = state.color
            )
            TextStr(modifier = Modifier.offset(y = (-12).dp), text = "GOAL")
            ChoiceGoal(
                modifier = Modifier.offset(y = (-24).dp),
                metricForHabit = state.metricForHabit,
                target = state.target,
                onEditClick = {},
                onChoiceSchedule = {}
            )
            TextStr(modifier = Modifier.offset(y = (-24).dp), text = "REMINDERS")
            ReminderBlock(
                modifier = Modifier.offset(y = (-32).dp),
                isEnabled = state.reminderIsActive,
                reminderTime = state.reminderTime,
                reminderDate = state.reminderDate,
                onSwitchClick = {},
                onChoiceSchedule = {}
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
    }
}

@Composable
private fun ReminderBlock(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    reminderTime: LocalTime,
    reminderDate: HabitSchedule,
    onSwitchClick: () -> Unit,
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

            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Remember to set off time for a workout today",
                style = MaterialTheme.typography.bodyMedium,
                color = Black40
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = {onSwitchClick()},
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
                .clickable { onChoiceSchedule() }
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
            Text(text = "Every day")//Подправить на Enum
        }
    }
}

@Composable
private fun ChoiceGoal(
    modifier: Modifier = Modifier,
    metricForHabit: HabitMetric,
    target: Int,
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
                    text = "$target ${metricForHabit.getUnitForm(target)}",
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
            Text(text = "Every day")
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
    habitColor: Color
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
            onClick = { }
        )
        ChoiceParametersForHabit(
            modifier = Modifier
                .weight(1f),
            habitName = "Orange", //  подправить отображение названия цвета
            choiceName = "Color",
            color = habitColor,
            onClick = {}
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
            Text(
                text = habitName,
                style = MaterialTheme.typography.bodyMedium
            )
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