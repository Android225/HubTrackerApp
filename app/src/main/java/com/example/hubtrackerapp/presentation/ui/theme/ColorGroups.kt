package com.example.hubtrackerapp.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.presentation.theme.*

data class ColorGroup(
    val name: String,
    val colors: List<Color>
)

val colorGroups = listOf(
    ColorGroup(
        name = "Blue",
        colors = listOf(
            Blue100, Blue80, Blue60, Blue40, Blue20, Blue10
        )
    ),
    ColorGroup(
        name = "Blue Info",
        colors = listOf(
            BlueInfo100, BlueInfo80, BlueInfo60, BlueInfo40, BlueInfo20, BlueInfo10
        )
    ),
    ColorGroup(
        name = "Red Error",
        colors = listOf(
            RedError100, RedError80, RedError60, RedError40, RedError20, RedError10
        )
    ),
    ColorGroup(
        name = "Green Success",
        colors = listOf(
            GreenSuccess100, GreenSuccess80, GreenSuccess60, GreenSuccess40, GreenSuccess20, GreenSuccess10
        )
    ),
    ColorGroup(
        name = "Orange Warning",
        colors = listOf(
            OrangeWarning100, OrangeWarning80, OrangeWarning60,
            OrangeWarning40, OrangeWarning20, OrangeWarning10
        )
    ),
    ColorGroup(
        name = "Purple",
        colors = listOf(
            Purple100, Purple80, Purple60, Purple40, Purple20, Purple10
        )
    ),
    ColorGroup(
        name = "Pink",
        colors = listOf(
            Pink100, Pink80, Pink60, Pink40, Pink20, Pink10
        )
    ),
    ColorGroup(
        name = "Teal",
        colors = listOf(
            Teal100, Teal80, Teal60, Teal40, Teal20, Teal10
        )
    ),
    ColorGroup(
        name = "Yellow",
        colors = listOf(
            Yellow100, Yellow80, Yellow60, Yellow40, Yellow20, Yellow10
        )
    ),
    ColorGroup(
        name = "Dark Blue",
        colors = listOf(
            DarkBlue100, DarkBlue80, DarkBlue60,
            DarkBlue40, DarkBlue20, DarkBlue10
        )
    )
)