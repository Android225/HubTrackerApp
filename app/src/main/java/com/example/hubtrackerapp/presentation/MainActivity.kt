package com.example.hubtrackerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hubtrackerapp.presentation.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavGraph()
         //   HomeScreen(
          //      onAddHabitClick = {}
           // )
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
