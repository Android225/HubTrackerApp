@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.hubtrackerapp.domain.hubbit.models.AddHabitMode
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black20
import com.example.hubtrackerapp.presentation.theme.Black40
import com.example.hubtrackerapp.presentation.theme.Black60
import com.example.hubtrackerapp.presentation.theme.Blue10
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.Blue20
import com.example.hubtrackerapp.presentation.theme.Blue80
import com.example.hubtrackerapp.presentation.theme.White100


@Composable
fun <T> ModSwitcher(
    modifier: Modifier = Modifier,
    selected: T,
    options: List<SwitcherOption<T>>,
    onModChange: (T) -> Unit,
    horizontalPadding: Dp = 24.dp
) {
    Row(
        modifier = modifier
            .padding(horizontal = horizontalPadding)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Black10)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        options.forEach { option ->
            ModeItem(
                modifier = Modifier.weight(1f),
                text = option.label,
                selected = selected == option.value,
                onClick = { onModChange(option.value) }
            )
        }
    }
}

data class SwitcherOption<T>(
    val label: String,
    val value: T
)

@Composable
fun ModeItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(3.dp)
            .background(
                if (selected) White100 else Color.Transparent,
                RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
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

@Composable
fun TextStr(
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
fun TextContent(
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

@Composable
fun <T> AddAndEditContent(
    modifier: Modifier = Modifier,
    nameForScreen: String,
    form: T,
    mode: AddHabitMode,
    backEvent: T,
    saveEvent: T,
    onEvent: (T) -> Unit,
    content: @Composable (PaddingValues) -> Unit

){
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
                            text = if(mode == AddHabitMode.CREATE_NEW) {
                                "Create $nameForScreen"
                            } else {
                                "Edit $nameForScreen"
                            } ,
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
                                    onEvent(backEvent)
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
                            onEvent(saveEvent)
                        },
                        shape = RoundedCornerShape(40.dp),

                        ) {
                        Text(
                            text = if(mode == AddHabitMode.CREATE_NEW || mode == AddHabitMode.CREATE_FROM_PREDEFINED ) {
                                "Add $nameForScreen"
                            } else {
                                "Save $nameForScreen"
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            content(innerPadding)
        }
    }
}

@Composable
fun ChoiceParameters(
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
fun <T> UniversalAnimatedPickerComp(
    pickerType: T,
    closeValue: T,
    onDismissClick: () -> Unit,
    content: @Composable () -> Unit,
    title: String // Название пикера сверху
) {
    val isVisible = pickerType != closeValue
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
fun IconPickerContentComp(
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
                CustomTextFieldForPickerComp(
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
            CreateHabitCardComp(
                icon = habit.icon,
                name = habit.habitName,
                onCardClick = { onAddHabit(habit) }
            )
        }
    }
}

@Composable
fun CreateHabitCardComp(
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
fun CustomTextFieldForPickerComp(
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