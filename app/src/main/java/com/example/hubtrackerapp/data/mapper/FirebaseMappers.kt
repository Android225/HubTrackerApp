package com.example.hubtrackerapp.data.mapper

import androidx.compose.ui.graphics.toArgb
import com.example.hubtrackerapp.data.db.model.HabitDbModel
import com.example.hubtrackerapp.data.db.model.HabitProgressDbModel
import com.example.hubtrackerapp.data.db.model.HabitScheduleDbModel
import com.example.hubtrackerapp.data.db.model.UserDbModel
import com.example.hubtrackerapp.data.firebase.model.FirebaseHabit
import com.example.hubtrackerapp.data.firebase.model.FirebaseHabitProgress
import com.example.hubtrackerapp.data.firebase.model.FirebaseUser
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar


fun UserDbModel.toFirebase(): FirebaseUser {
    return FirebaseUser(
        userId = userId,
        email = email,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate, // "1990-01-01" - оставляем как String
        gender = gender
        // createdAt установится автоматически
    )
}

fun FirebaseUser.toDbModel(password: String = ""): UserDbModel {
    return UserDbModel(
        userId = userId,
        email = email,
        firstName = firstName,
        lastName = lastName,
        birthDate = birthDate,
        gender = gender
    )
}


fun HabitDbModel.toFirebase(): FirebaseHabit {
    return FirebaseHabit(
        habitId = habitId,
        userId = userId,
        emoji = emoji,
        title = title,
        // LocalDate → Long
        createdAt = localDateToTimestamp(createdAt),
        schedule = schedule.toFirebaseMap(),
        isArchived = isArchived,
        color = color.toArgb(),
        target = target,
        metric = metric.name,
        reminderTime = reminderTime.toString(), // "10:30"
        reminderSchedule = reminderDate.toFirebaseMap(),
        reminderIsActive = reminderIsActive,
        habitType = habitType.name,
        habitCustom = habitCustom
    )
}

fun FirebaseHabit.toDbModel(): HabitDbModel {
    return HabitDbModel(
        habitId = habitId,
        userId = userId,
        emoji = emoji,
        title = title,
        // Long → LocalDate
        createdAt = timestampToLocalDate(createdAt),
        schedule = schedule?.toDbSchedule() ?: HabitScheduleDbModel("EVERY_DAY"),
        isArchived = isArchived,
        color = androidx.compose.ui.graphics.Color(color),
        target = target,
        metric = HabitMetric.valueOf(metric),
        // String → LocalTime
        reminderTime = LocalTime.parse(reminderTime),
        reminderDate = reminderSchedule?.toDbSchedule()
            ?: HabitScheduleDbModel("EVERY_DAY"),
        reminderIsActive = reminderIsActive,
        habitType = ModeForSwitchInHabit.valueOf(habitType),
        habitCustom = habitCustom
    )
}

fun HabitProgressDbModel.toFirebase(): FirebaseHabitProgress {
    return FirebaseHabitProgress(
        habitId = habitId,
        date = date.toString(), // "2024-01-01"
        isCompleted = isCompleted,
        progress = progress,
        progressWithTarget = progressWithTarget,
        skipped = skipped,
        failed = failed
    )
}

fun FirebaseHabitProgress.toDbModel(): HabitProgressDbModel {
    return HabitProgressDbModel(
        habitId = habitId,
        // String → LocalDate
        date = LocalDate.parse(date),
        isCompleted = isCompleted,
        progress = progress,
        progressWithTarget = progressWithTarget,
        skipped = skipped,
        failed = failed
    )
}


fun HabitScheduleDbModel.toFirebaseMap(): Map<String, Any> {
    return mapOf(
        "type" to type,
        "n" to (n ?: 0), // Храним 0 вместо null
        "days" to (days ?: emptyList<Int>())
    )
}

fun Map<String, Any>.toDbSchedule(): HabitScheduleDbModel {
    val nValue = this["n"]
    return HabitScheduleDbModel(
        type = this["type"] as? String ?: "EVERY_DAY",
        n = when (nValue) {
            is Int -> if (nValue == 0) null else nValue
            is Long -> if (nValue == 0L) null else nValue.toInt()
            is String -> nValue.toIntOrNull()?.takeIf { it != 0 }
            else -> null
        },
        days = this["days"] as? List<Int>
    )
}

private fun localDateToTimestamp(localDate: LocalDate): Long {
    return try {
        localDate.atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    } catch (e: Exception) {
        val calendar = Calendar.getInstance()
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.timeInMillis
    }
}

private fun timestampToLocalDate(timestamp: Long): LocalDate {
    return try {
        java.time.Instant.ofEpochMilli(timestamp)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } catch (e: Exception) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        LocalDate.of(year, month, day)
    }
}


private fun localDateToString(localDate: LocalDate): String {
    return localDate.toString() // "2024-01-01"
}

private fun stringToLocalDate(dateString: String): LocalDate {
    return LocalDate.parse(dateString)
}