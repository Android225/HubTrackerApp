@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.hubtrackerapp.presentation.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hubtrackerapp.presentation.theme.Black10
import com.example.hubtrackerapp.presentation.theme.Black20
import com.example.hubtrackerapp.presentation.theme.Black60
import com.example.hubtrackerapp.presentation.theme.Blue10
import com.example.hubtrackerapp.presentation.theme.Blue100
import com.example.hubtrackerapp.presentation.theme.White100


@Composable
fun AuthorizationHubScreen(
    modifier: Modifier = Modifier,
    onFinished: () -> Unit
) {

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
                        text = "Continue with E-mail",
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
                                onFinished()
                                TODO("THIS IS BUTTON BACK NOT A NEXT NOT A FINISHED!")
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable{
                            TODO()
                        },
                    text = "Dont't have account? Let's create",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Blue100,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                        onFinished()
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
        var tess: String = "123"
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

            Text(
                modifier = Modifier,
                text = "E-MAIL",
                style = MaterialTheme.typography.labelSmall
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = tess,
                onValueChange = {
                    TODO()
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                    //Доделать цвета
                //focusedIndicatorColor = Color.Transparent,
                    //unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge,
                placeholder = {
                    Text(
                        text = "Enter your E-Mail",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black20
                    )
                }
            )

            Text(
                modifier = Modifier,
                text = "Password",
                style = MaterialTheme.typography.labelSmall
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = tess,
                onValueChange = {
                    TODO()
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                    //Доделать цвета
                    //focusedIndicatorColor = Color.Transparent,
                    //unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = MaterialTheme.typography.titleLarge,
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = "Enter your password",
                        style = MaterialTheme.typography.titleLarge,
                        color = Black20
                    )
                }
            )
            Text(
                modifier = Modifier
                    .clickable{
                        TODO()
                    },
                text = "I forgot my password",
                style = MaterialTheme.typography.bodyMedium,
                color = Black60
            )
        }
    }
}