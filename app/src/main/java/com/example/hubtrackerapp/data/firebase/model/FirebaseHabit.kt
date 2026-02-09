package com.example.hubtrackerapp.data.firebase.model

data class FirebaseHabit(
    val habitId: String = "",
    val userId: String = "",
    val emoji: String = "",
    val title: String = "",
    val createdAt: Long = System.currentTimeMillis(), // LocalDate → Long
    val schedule: Map<String, Any>? = null, // HabitSchedule → Map
    val isArchived: Boolean = false,
    val color: Int = 0, // Color → Int (ARGB)
    val target: String = "",
    val metric: String = "", // HabitMetric → String (enum.name)
    val reminderTime: String = "", // LocalTime → String "HH:mm"
    val reminderSchedule: Map<String, Any>? = null,
    val reminderIsActive: Boolean = false,
    val habitType: String = "", // ModeForSwitchInHabit → String
    val habitCustom: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis() // Для разрешения конфликтов
)