package com.example.hubtrackerapp.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.hubtrackerapp.presentation.theme.Blue10
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.Blue20
import com.example.hubtrackerapp.presentation.theme.Blue40
import com.example.hubtrackerapp.presentation.theme.Blue60
import com.example.hubtrackerapp.presentation.theme.Blue80
import com.example.hubtrackerapp.presentation.theme.BlueInfo10
import com.example.hubtrackerapp.presentation.theme.BlueInfo100
import com.example.hubtrackerapp.presentation.theme.BlueInfo20
import com.example.hubtrackerapp.presentation.theme.BlueInfo40
import com.example.hubtrackerapp.presentation.theme.BlueInfo60
import com.example.hubtrackerapp.presentation.theme.BlueInfo80
import com.example.hubtrackerapp.presentation.theme.DarkBlue10
import com.example.hubtrackerapp.presentation.theme.DarkBlue100
import com.example.hubtrackerapp.presentation.theme.DarkBlue20
import com.example.hubtrackerapp.presentation.theme.DarkBlue40
import com.example.hubtrackerapp.presentation.theme.DarkBlue60
import com.example.hubtrackerapp.presentation.theme.DarkBlue80
import com.example.hubtrackerapp.presentation.theme.GreenSuccess10
import com.example.hubtrackerapp.presentation.theme.GreenSuccess100
import com.example.hubtrackerapp.presentation.theme.GreenSuccess20
import com.example.hubtrackerapp.presentation.theme.GreenSuccess40
import com.example.hubtrackerapp.presentation.theme.GreenSuccess60
import com.example.hubtrackerapp.presentation.theme.GreenSuccess80
import com.example.hubtrackerapp.presentation.theme.OrangeWarning10
import com.example.hubtrackerapp.presentation.theme.OrangeWarning100
import com.example.hubtrackerapp.presentation.theme.OrangeWarning20
import com.example.hubtrackerapp.presentation.theme.OrangeWarning40
import com.example.hubtrackerapp.presentation.theme.OrangeWarning60
import com.example.hubtrackerapp.presentation.theme.OrangeWarning80
import com.example.hubtrackerapp.presentation.theme.Pink10
import com.example.hubtrackerapp.presentation.theme.Pink100
import com.example.hubtrackerapp.presentation.theme.Pink20
import com.example.hubtrackerapp.presentation.theme.Pink40
import com.example.hubtrackerapp.presentation.theme.Pink60
import com.example.hubtrackerapp.presentation.theme.Pink80
import com.example.hubtrackerapp.presentation.theme.Purple10
import com.example.hubtrackerapp.presentation.theme.Purple100
import com.example.hubtrackerapp.presentation.theme.Purple20
import com.example.hubtrackerapp.presentation.theme.Purple40
import com.example.hubtrackerapp.presentation.theme.Purple60
import com.example.hubtrackerapp.presentation.theme.Purple80
import com.example.hubtrackerapp.presentation.theme.RedError10
import com.example.hubtrackerapp.presentation.theme.RedError100
import com.example.hubtrackerapp.presentation.theme.RedError20
import com.example.hubtrackerapp.presentation.theme.RedError40
import com.example.hubtrackerapp.presentation.theme.RedError60
import com.example.hubtrackerapp.presentation.theme.RedError80
import com.example.hubtrackerapp.presentation.theme.Teal10
import com.example.hubtrackerapp.presentation.theme.Teal100
import com.example.hubtrackerapp.presentation.theme.Teal20
import com.example.hubtrackerapp.presentation.theme.Teal40
import com.example.hubtrackerapp.presentation.theme.Teal60
import com.example.hubtrackerapp.presentation.theme.Teal80
import com.example.hubtrackerapp.presentation.theme.Yellow10
import com.example.hubtrackerapp.presentation.theme.Yellow100
import com.example.hubtrackerapp.presentation.theme.Yellow20
import com.example.hubtrackerapp.presentation.theme.Yellow40
import com.example.hubtrackerapp.presentation.theme.Yellow60
import com.example.hubtrackerapp.presentation.theme.Yellow80

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