package com.example.hubtrackerapp.domain.hubbit.models.forUi

import HabitMetric
import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.SwipeHabitState

data class HabitWithProgressUi (
    val habitId: String,
    val emoji: String,
    val title: String,
    val isCompleted: Boolean,
    val progress: Float,
    val color: Color,
    val target: String, // к чему идем goal target
    val metric: HabitMetric,
    val habitType: ModeForSwitchInHabit,
    val progressWithTarget: String, // сколько выполнили,
    val skipped: Boolean,
    val failed: Boolean,
    val swipeState: SwipeHabitState = SwipeHabitState.CLOSED
)