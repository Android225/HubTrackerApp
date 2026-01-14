// domain/habit/models/HabitMetric.kt
enum class HabitMetric(
    val displayName: String,
    val unit: String,          // "минута"
    val unitPlural: String,    // "минут"
    val category: MetricCategory,
    val iconEmoji: String = "📊"
) {
    MINUTES("Минуты", "минута", "минут", MetricCategory.TIME, "⏱️"),
    HOURS("Часы", "час", "часов", MetricCategory.TIME, "🕐"),
    KILOMETERS("Километры", "километр", "километров", MetricCategory.DISTANCE, "🏃"),
    METERS("Метры", "метр", "метров", MetricCategory.DISTANCE, "📏"),
    TIMES("Раз(ы)", "раз", "раз", MetricCategory.COUNT, "🔢"),
    PAGES("Страницы", "страница", "страниц", MetricCategory.COUNT, "📖"),
    LITERS("Литр(ы)", "литр", "литров", MetricCategory.VOLUME, "💧"),
    GLASSES("Стаканы", "стакан", "стаканов", MetricCategory.VOLUME, "🥛"),
    KILOGRAMS("Килограммы", "килограмм", "килограммов", MetricCategory.WEIGHT, "🏋️"),
    REPS("Повторения", "повторение", "повторений", MetricCategory.WEIGHT, "💪"),
    SETS("Подходы", "подход", "подходов", MetricCategory.WEIGHT, "⚡"),
    POINTS("Очки", "очко", "очков", MetricCategory.MENTAL, "🎯"),
    CALORIES("Калории", "калория", "калорий", MetricCategory.HEALTH, "🍎");

    // Логика склонения
    fun getUnitForm(quantity: String): String {
        if (quantity == "" ){
            return displayName
        }

        val quantityInt = quantity.toInt()

        val mod10 = quantityInt % 10
        val mod100 = quantityInt % 100

        return when {
            mod10 == 1 && mod100 != 11 -> unit
            mod10 in 2..4 && mod100 !in 12..14 -> unit
            else -> unitPlural
        }
    }

    fun formatWithQuantity(quantity: String): String = "$quantity ${getUnitForm(quantity)}"
}

// Категории
enum class MetricCategory(val displayName: String, val icon: String) {
    TIME("Время", "⏱️"),
    DISTANCE("Расстояние", "🏃"),
    COUNT("Количество", "🔢"),
    VOLUME("Объём", "💧"),
    WEIGHT("Вес и сила", "🏋️"),
    MENTAL("Умственное", "🧠"),
    HEALTH("Здоровье", "❤️")
}