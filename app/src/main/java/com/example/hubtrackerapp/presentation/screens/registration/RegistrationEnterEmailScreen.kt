@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.authorization

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.hubtrackerapp.presentation.screens.registration.RegisterEvent
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationViewModel
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black20
import com.example.hubtrackerapp.presentation.theme.White100


@Composable
fun RegistrationEnterEmailScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextStep: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 8.dp,
                    bottom = 16.dp
                ),
                title = {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color = Black10
                            )
                            .clickable{
                                onBackClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .background(White100),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "button back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp, top = 16.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    enabled = state.isRegisterEnabled,
                    onClick = {
                        Log.d("Registration","Register Click")
                        viewModel.onEventRegister(RegisterEvent.RegisterUser)
                        onNextStep()
                    },
                    shape = RoundedCornerShape(40.dp),

                    ) {
                    Text(
                        text = "Next"
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .height(1.dp)
                .background(Black20)
        )

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.padding(4.dp))

            TextStr(text = "EMAIL")
            TextContent(
                text = state.registrationDraft.email,
                textPlace = "Enter your email",
                onTextChanged = {
                    Log.d("Register","email - $it")
                    viewModel.onEventRegister(RegisterEvent.SetEmail(it))
                }
            )
            TextStr(text = "Password")
            TextContent(
                text = state.registrationDraft.password,
                textPlace = "Enter your password",
                visualTransformation = PasswordVisualTransformation(),
                onTextChanged = {
                    viewModel.onEventRegister(RegisterEvent.SetPassword(it))
                }
            )
        }
    }
}


@Composable
private fun TextStr(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelSmall
    )
}
@Composable
private fun TextContent(
    modifier: Modifier = Modifier,
    text: String,
    textPlace: String,
    onTextChanged: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        value = text,
        onValueChange = onTextChanged,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
            //Доделать цвета
            //focusedIndicatorColor = Color.Transparent,
            //unfocusedIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        textStyle = MaterialTheme.typography.titleLarge,
        placeholder = {
            Text(
                text = textPlace,
                style = MaterialTheme.typography.titleLarge,
                color = Black20
            )
        }
    )
}