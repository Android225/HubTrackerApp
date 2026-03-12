package com.example.hubtrackerapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.hubtrackerapp.data.db.HabitsDatabase
import com.example.hubtrackerapp.data.db.dao.UserDao
import com.example.hubtrackerapp.presentation.navigation.NavGraph
import com.example.hubtrackerapp.presentation.navigation.Screen
import com.example.hubtrackerapp.presentation.screens.test.TestScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val userDao: UserDao by lazy {
        HabitsDatabase.getInstance(applicationContext).userDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            //отслеживаем авторизацию
            var isLoggedIn by remember { mutableStateOf(false) }
            var isLoading by remember { mutableStateOf(true) }

            // Проверяем наличие пользователя в Room
            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    isLoggedIn = userDao.getCurrentUser() != null
                }
                isLoading = false
            }

            if (isLoading) {
                // Крутилка загрузки
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                NavGraph(
                    startDestination = if (isLoggedIn) {
                        Screen.HomeScreen.route  // если авторизован - сразу на home
                    } else {
                        Screen.Authorization.route // если нет - на авторизацию
                    }
                )
            }
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
