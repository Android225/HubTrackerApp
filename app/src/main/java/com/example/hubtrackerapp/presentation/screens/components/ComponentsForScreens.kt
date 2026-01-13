package com.example.hubtrackerapp.presentation.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitch
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black60
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.White100

@Composable
fun ModSwitcher(
    modifier: Modifier = Modifier,
    selected: ModeForSwitch,
    textFirstSwitch: String = "Today",
    selectedFirst:ModeForSwitch = ModeForSwitch.HOBBIES,
    selectedSecond:ModeForSwitch = ModeForSwitch.CLUBS,
    textSecondSwitch: String = "Clubs",
    onModChange: (ModeForSwitch) -> Unit,
    horizontalPadding: Dp = 24.dp
) {
    Row(
        modifier = modifier
            .padding(horizontal = horizontalPadding)
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Black10)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ModeItem(
            modifier = Modifier.weight(1f),
            text = textFirstSwitch,
            selected = selected == selectedFirst,
            onClick = { onModChange(selectedFirst) }
        )
        ModeItem(
            modifier = Modifier.weight(1f),
            text = textSecondSwitch,
            selected = selected == selectedSecond,
            onClick = { onModChange(selectedSecond) }
        )

    }
}


@Composable
fun ModeItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(3.dp)
            .background(
                if (selected) White100 else Color.Transparent,
                RoundedCornerShape(32.dp)
            )
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .padding(5.dp),

        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) Blue100 else Black60
        )
    }
}