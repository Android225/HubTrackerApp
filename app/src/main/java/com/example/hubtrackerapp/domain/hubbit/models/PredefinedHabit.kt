package com.example.hubtrackerapp.domain.hubbit.models

import HabitMetric
import androidx.compose.ui.graphics.Color
import java.time.LocalTime

data class PredefinedHabit(
    val habitName: String,// название хобби title
    val icon: String, // иконка к хобби emoji
    val color: Color, //цвет для хобби  --
    val metricForHabit: HabitMetric,  //--
    val target: String, // целевая велечина --
    val habitSchedule: HabitSchedule, // schedule ++ дни в которые будут отображаться задачи и приходить уведомления
    val reminderTime: LocalTime, // -- время во сколько будет приходить уведомление
    val reminderDate: HabitSchedule, // -- дни в которые будут приходить уведомления для данной задачи
    val reminderIsActive: Boolean = false, // -- напоминания включены или выключены
    val habitType: ModeForSwitchInHabit = ModeForSwitchInHabit.BUILD, // --
    val habitCustom: Boolean = false // --
)