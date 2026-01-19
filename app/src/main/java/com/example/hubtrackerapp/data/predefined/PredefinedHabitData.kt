package com.example.hubtrackerapp.data.predefined

import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.Blue60
import com.example.hubtrackerapp.presentation.theme.Blue80
import com.example.hubtrackerapp.presentation.theme.DarkBlue100
import com.example.hubtrackerapp.presentation.theme.GreenSuccess100
import com.example.hubtrackerapp.presentation.theme.GreenSuccess40
import com.example.hubtrackerapp.presentation.theme.GreenSuccess60
import com.example.hubtrackerapp.presentation.theme.GreenSuccess80
import com.example.hubtrackerapp.presentation.theme.Orange100
import com.example.hubtrackerapp.presentation.theme.Orange40
import com.example.hubtrackerapp.presentation.theme.Orange60
import com.example.hubtrackerapp.presentation.theme.Orange80
import com.example.hubtrackerapp.presentation.theme.Pink100
import com.example.hubtrackerapp.presentation.theme.Pink60
import com.example.hubtrackerapp.presentation.theme.Pink80
import com.example.hubtrackerapp.presentation.theme.Purple100
import com.example.hubtrackerapp.presentation.theme.Purple60
import com.example.hubtrackerapp.presentation.theme.Purple80
import com.example.hubtrackerapp.presentation.theme.Teal100
import com.example.hubtrackerapp.presentation.theme.Teal80
import com.example.hubtrackerapp.presentation.theme.Yellow100
import com.example.hubtrackerapp.presentation.theme.Yellow80
import java.time.DayOfWeek
import java.time.LocalTime

object PredefinedHabitData {
    val habits = listOf(
        // 🏃‍♀️ СПОРТ И ФИТНЕС
        PredefinedHabit(
            habitName = "Утренняя пробежка",
            icon = "🏃",
            color = Orange100,
            metricForHabit = HabitMetric.KILOMETERS,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            reminderTime = LocalTime.of(7, 30),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "5" // км
        ),
        PredefinedHabit(
            habitName = "Зарядка",
            icon = "💪",
            color = Orange80,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.EveryNDays(2),
            reminderTime = LocalTime.of(8, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "15" // минут
        ),
        PredefinedHabit(
            habitName = "Тренировка в зале",
            icon = "🏋️",
            color = Orange60,
            metricForHabit = HabitMetric.SETS,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY
                )
            ),
            reminderTime = LocalTime.of(18, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "4" // подхода
        ),
        PredefinedHabit(
            habitName = "Йога / Растяжка",
            icon = "🧘",
            color = Orange40,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            reminderTime = LocalTime.of(9, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "20" // минут
        ),

        // 📚 ОБУЧЕНИЕ И РАЗВИТИЕ
        PredefinedHabit(
            habitName = "Чтение книги",
            icon = "📚",
            color = Blue100,
            metricForHabit = HabitMetric.PAGES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(21, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "30" // страниц
        ),
        PredefinedHabit(
            habitName = "Изучение языка",
            icon = "🗣️",
            color = Blue80,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(19, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "30" // минут
        ),
        PredefinedHabit(
            habitName = "Программирование",
            icon = "💻",
            color = Blue60,
            metricForHabit = HabitMetric.HOURS,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
                )
            ),
            reminderTime = LocalTime.of(10, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                    DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "2" // часа
        ),

        // 💧 ЗДОРОВЬЕ
        PredefinedHabit(
            habitName = "Пить воду",
            icon = "💧",
            color = GreenSuccess100,
            metricForHabit = HabitMetric.GLASSES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(9, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "8" // стаканов
        ),
        PredefinedHabit(
            habitName = "Ранний отход ко сну",
            icon = "😴",
            color = GreenSuccess80,
            metricForHabit = HabitMetric.TIMES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(22, 30),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "1" // раз
        ),
        PredefinedHabit(
            habitName = "Здоровый завтрак",
            icon = "🍎",
            color = GreenSuccess60,
            metricForHabit = HabitMetric.TIMES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(8, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "1" // раз
        ),
        PredefinedHabit(
            habitName = "Медитация",
            icon = "🧠",
            color = GreenSuccess40,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(7, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "10" // минут
        ),

        // 💰 ФИНАНСЫ
        PredefinedHabit(
            habitName = "Вести бюджет",
            icon = "💰",
            color = Yellow100,
            metricForHabit = HabitMetric.TIMES,
            habitSchedule = HabitSchedule.SpecificDays(setOf(DayOfWeek.SUNDAY)),
            reminderTime = LocalTime.of(20, 0),
            reminderDate = HabitSchedule.SpecificDays(setOf(DayOfWeek.SUNDAY)),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "1" // раз
        ),
        PredefinedHabit(
            habitName = "Откладывать деньги",
            icon = "🏦",
            color = Yellow80,
            metricForHabit = HabitMetric.TIMES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.FRIDAY
                )
            ),
            reminderTime = LocalTime.of(18, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.FRIDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "2" // раза в неделю
        ),

        // 🏠 БЫТ И ОТНОШЕНИЯ
        PredefinedHabit(
            habitName = "Уборка в комнате",
            icon = "🧹",
            color = Purple100,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(setOf(DayOfWeek.SATURDAY)),
            reminderTime = LocalTime.of(11, 0),
            reminderDate = HabitSchedule.SpecificDays(setOf(DayOfWeek.SATURDAY)),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "30" // минут
        ),
        PredefinedHabit(
            habitName = "Звонок родителям",
            icon = "📞",
            color = Purple80,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(setOf(DayOfWeek.SUNDAY)),
            reminderTime = LocalTime.of(19, 0),
            reminderDate = HabitSchedule.SpecificDays(setOf(DayOfWeek.SUNDAY)),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "15" // минут
        ),
        PredefinedHabit(
            habitName = "Готовить еду дома",
            icon = "🍳",
            color = Purple60,
            metricForHabit = HabitMetric.TIMES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            reminderTime = LocalTime.of(18, 30),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "3" // раза в неделю
        ),

        // 🎨 ХОББИ И ТВОРЧЕСТВО
        PredefinedHabit(
            habitName = "Рисование",
            icon = "🎨",
            color = Pink100,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY
                )
            ),
            reminderTime = LocalTime.of(20, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.TUESDAY, DayOfWeek.THURSDAY, DayOfWeek.SATURDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "45" // минут
        ),
        PredefinedHabit(
            habitName = "Игра на инструменте",
            icon = "🎸",
            color = Pink80,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            reminderTime = LocalTime.of(19, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "40" // минут
        ),
        PredefinedHabit(
            habitName = "Ведение дневника",
            icon = "📔",
            color = Pink60,
            metricForHabit = HabitMetric.PAGES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(22, 0),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.QUIT,
            target = "2" // страницы
        ),

        // 🎯 ПРОДУКТИВНОСТЬ
        PredefinedHabit(
            habitName = "Планирование дня",
            icon = "📝",
            color = Teal100,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.EveryDay,
            reminderTime = LocalTime.of(8, 30),
            reminderDate = HabitSchedule.EveryDay,
            habitType = ModeForSwitchInHabit.BUILD,
            target = "10" // минут
        ),
        PredefinedHabit(
            habitName = "Изучение нового навыка",
            icon = "🚀",
            color = Teal80,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.TUESDAY, DayOfWeek.THURSDAY
                )
            ),
            reminderTime = LocalTime.of(17, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.TUESDAY, DayOfWeek.THURSDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "60" // минут
        ),

        // 🎮 ОТДЫХ И РАЗВЛЕЧЕНИЯ
        PredefinedHabit(
            habitName = "Прогулка на свежем воздухе",
            icon = "🌳",
            color = DarkBlue100,
            metricForHabit = HabitMetric.MINUTES,
            habitSchedule = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
                )
            ),
            reminderTime = LocalTime.of(15, 0),
            reminderDate = HabitSchedule.SpecificDays(
                setOf(
                    DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
                )
            ),
            habitType = ModeForSwitchInHabit.BUILD,
            target = "60" // минут
        )
    )
}