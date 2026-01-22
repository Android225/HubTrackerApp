package com.example.hubtrackerapp.presentation

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.hubtrackerapp.presentation.navigation.NavGraph
import com.example.hubtrackerapp.presentation.screens.add.AddHabit
import com.example.hubtrackerapp.presentation.screens.authorization.AuthorizationHubScreen
import com.example.hubtrackerapp.presentation.screens.home.HomeScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationChooseHabbitsScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationChoseGenderScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationScreen
import com.example.hubtrackerapp.presentation.ui.theme.HubTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
         //   NavGraph()
            HomeScreen(
                onAddHabitClick = {}
            )
            //AddHabit(onBackClick = {})
        }
    }
}


/*HubTrackerAppTheme {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    AuthorizationHubScreen(
//                        onFinished = {  }
//                    )
//                    RegistrationScreen(
//                        onNextStep = {}
//                    )
//                    RegistrationChoseGenderScreen(
//                        onNextStep = {}
//                    )
        RegistrationChooseHabbitsScreen(
            onNextStep = {}
        )

    }
}*/
