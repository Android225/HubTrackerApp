package com.example.hubtrackerapp.presentation.screens.authorization

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AuthorizationHubScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Continue with E-mail",
                        fontSize = 20.sp
                    )
                }
            )
        }
    ) { }
}