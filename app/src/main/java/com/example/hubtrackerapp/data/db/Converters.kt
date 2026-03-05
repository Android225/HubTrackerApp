package com.example.hubtrackerapp.data.db

import HabitMetric
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.room.TypeConverter
import com.example.hubtrackerapp.data.db.model.HabitScheduleDbModel
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeGoalType
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ChallengeVisibility
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.InvitationStatus
import com.example.hubtrackerapp.domain.hubbit.models.challenges.model.ParticipantStatus
import com.example.hubtrackerapp.domain.hubbit.models.club.model.RoleMode
import com.example.hubtrackerapp.domain.hubbit.models.friends.model.RequestStatus
import com.example.hubtrackerapp.domain.hubbit.models.statistic.model.ActionType
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


    @TypeConverter
    fun fromRequestStatus(status: RequestStatus?): String? = status?.name

    @TypeConverter
    fun toRequestStatus(value: String?): RequestStatus? =
        value?.let { RequestStatus.valueOf(it) }

    @TypeConverter
    fun fromActionType(type: ActionType?): String? = type?.name

    @TypeConverter
    fun toActionType(value: String?): ActionType? =
        value?.let { ActionType.valueOf(it) }

    @TypeConverter
    fun fromRoleMode(type: RoleMode?): String? = type?.name

    @TypeConverter
    fun toRoleMode(value: String?): RoleMode? =
        value?.let { RoleMode.valueOf(it) }

    @TypeConverter
    fun fromChallengeVisibility(visibility: ChallengeVisibility?): String? =
        visibility?.name

    @TypeConverter
    fun toChallengeVisibility(value: String?): ChallengeVisibility? =
        value?.let { ChallengeVisibility.valueOf(it) }

    @TypeConverter
    fun fromChallengeGoalType(goalType: ChallengeGoalType?): String? =
        goalType?.name

    @TypeConverter
    fun toChallengeGoalType(value: String?): ChallengeGoalType? =
        value?.let { ChallengeGoalType.valueOf(it) }

    @TypeConverter
    fun fromInvitationStatus(status: InvitationStatus?): String? =
        status?.name

    @TypeConverter
    fun toInvitationStatus(value: String?): InvitationStatus? =
        value?.let { InvitationStatus.valueOf(it) }

    @TypeConverter
    fun fromParticipantStatus(status: ParticipantStatus?): String? =
        status?.name

    @TypeConverter
    fun toParticipantStatus(value: String?): ParticipantStatus? =
        value?.let { ParticipantStatus.valueOf(it) }

}