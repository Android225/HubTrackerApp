package com.example.hubtrackerapp.data.db

import HabitMetric
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter
import com.example.hubtrackerapp.data.db.model.HabitScheduleDbModel
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalTime

class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? =
        time?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? =
        value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun fromColor(color: Color?): Int? =
        color?.toArgb()

    @TypeConverter
    fun toColor(value: Int?): Color? =
        value?.let { Color(it) }

    @TypeConverter
    fun fromHabitMetric(metric: HabitMetric?): String? =
        metric?.name

    @TypeConverter
    fun toHabitMetric(value: String?): HabitMetric? =
        value?.let { HabitMetric.valueOf(it) }

    @TypeConverter
    fun fromModeForSwitch(value: ModeForSwitchInHabit?): String? =
        value?.name

    @TypeConverter
    fun toModeForSwitch(value: String?): ModeForSwitchInHabit? =
        value?.let { ModeForSwitchInHabit.valueOf(it) }

    @TypeConverter
    fun fromHabitSchedule(schedule: HabitScheduleDbModel?): String? =
        schedule?.let { Gson().toJson(it) }

    @TypeConverter
    fun toHabitSchedule(value: String?): HabitScheduleDbModel? =
        value?.let { Gson().fromJson(it, HabitScheduleDbModel::class.java) }
}