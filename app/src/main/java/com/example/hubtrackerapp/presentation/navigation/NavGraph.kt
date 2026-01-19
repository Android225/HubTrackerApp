package com.example.hubtrackerapp.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hubtrackerapp.presentation.screens.add.AddHabit
import com.example.hubtrackerapp.presentation.screens.authorization.AuthorizationHubScreen
import com.example.hubtrackerapp.presentation.screens.authorization.RegistrationEnterEmailScreen
import com.example.hubtrackerapp.presentation.screens.home.HomeScreen
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
           Log.d("Navigate","Screen.Authorization.route")
            AuthorizationHubScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    Log.d("Navigate","goToHomeScreen")
                    navController.navigate(Screen.HomeScreen.route){
                        popUpTo(Screen.HomeScreen.route){
                            inclusive = true
                        }
                    }
                },
                onRegisterAccount = {
                    Log.d("Navigate","RegisterAccount")
                    navController.navigate(Screen.RegistrationData.route)
                }
            )
        }
        composable(Screen.RegistrationData.route) {
            Log.d("Navigate","RegistrationScreen")
            RegistrationScreen(
                onNextStep = {
                    Log.d("Navigate","goToChoseGender")
                    navController.navigate(Screen.RegistrationGender.route)
                },
                onBackClick = {
                    Log.d("Navigate","goBack")
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.RegistrationGender.route) {
            Log.d("Navigate","RegistrationChoseGenderScreen")
            RegistrationChoseGenderScreen(
                onNextStep = {
                    Log.d("Navigate","goToChoseHabits")
                    navController.navigate(Screen.RegistrationHabits.route)
                },
                onBackClick = {
                    Log.d("Navigate","goBack")
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.RegistrationHabits.route) {
            Log.d("Navigate","RegistrationChooseHabbitsScreen")
            RegistrationChooseHabbitsScreen(
                onRegister = {
                    Log.d("Navigate","gotoEnterEmailAndPassword")
                    navController.navigate(Screen.RegistrationEmail.route)
                },
                onBackClick = {
                    Log.d("Navigate","goBack")
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.RegistrationEmail.route) {
            Log.d("Navigate","RegistrationEnterEmailScreen")
            RegistrationEnterEmailScreen(
                onNextStep = {
                    Log.d("Navigate","gotoEnterEmailAndPassword")
                    navController.navigate(Screen.Authorization.route){
                        popUpTo(Screen.Authorization.route){
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    Log.d("Navigate","goBack")
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.HomeScreen.route) {
            HomeScreen(
                onAddHabitClick = {
                    navController.navigate(Screen.AddHabit.route)
                }
            )
        }
        composable(Screen.AddHabit.route) {
            AddHabit(
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
    data object HomeScreen: Screen("home")
    data object AddHabit: Screen("add_habit")

}