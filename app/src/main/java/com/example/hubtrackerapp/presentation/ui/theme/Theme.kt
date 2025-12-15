package com.example.hubtrackerapp.presentation.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.hubtrackerapp.presentation.theme.Black100
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.Pink100
import com.example.hubtrackerapp.presentation.theme.Purple100
import com.example.hubtrackerapp.presentation.theme.RedError100
import com.example.hubtrackerapp.presentation.theme.Typography
import com.example.hubtrackerapp.presentation.theme.White100

private val DarkColorScheme = darkColorScheme()

private val LightColorScheme = lightColorScheme(
    primary = Blue100,          // Основной синий
    secondary = Purple100,      // Акцентный фиолетовый
    tertiary = Pink100,         // Третичный розовый
    background = White100,      // Фон темной темы
    surface = White100,         // Поверхности
    onPrimary = White100,       // Текст на primary кнопках
    onSecondary = White100,     // Текст на secondary
    onBackground = Black100,    // Основной текст
    onSurface = Black100,       // Текст на поверхностях
    error = RedError100,        // Цвет ошибок
    onError = White100          // Текст на ошибках
)

@Composable
fun HubTrackerAppTheme(
    darkTheme: Boolean = false,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}