package com.example.hubtrackerapp.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hubtrackerapp.presentation.screens.authorization.AuthorizationHubScreen
import com.example.hubtrackerapp.presentation.screens.authorization.RegistrationEnterEmailScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationChooseHabbitsScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationChoseGenderScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Authorization.route
    ) {
        composable(Screen.Authorization.route) {
            AuthorizationHubScreen(
                onFinished = {
                },
                onRegisterAccount = {
                    navController.navigate(Screen.RegistrationEmail.route)
                }
            )
        }

        composable(Screen.RegistrationEmail.route) {
            RegistrationEnterEmailScreen(
                onNextStep = {
                    navController.navigate(Screen.RegistrationData.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.RegistrationData.route) {
            RegistrationScreen(
                onNextStep = {
                    navController.navigate(Screen.RegistrationGender.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.RegistrationGender.route) {
            RegistrationChoseGenderScreen(
                onNextStep = {
                    navController.navigate(Screen.RegistrationHabits.route)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.RegistrationHabits.route) {
            RegistrationChooseHabbitsScreen(
                onRegister = {
                    navController.navigate(Screen.Authorization.route){
                        popUpTo(Screen.Authorization.route){
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {

    data object Authorization : Screen("auth")

    data object RegistrationEmail : Screen("reg_email")
    data object RegistrationData : Screen("reg_data")
    data object RegistrationGender : Screen("reg_gender")
    data object RegistrationHabits : Screen("reg_habits")


}