package com.example.hubtrackerapp.presentation.navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.hubtrackerapp.domain.hubbit.models.PredefinedHabit
import com.example.hubtrackerapp.domain.hubbit.models.forUi.HabitWithProgressUi
import com.example.hubtrackerapp.presentation.screens.add.AddHabit
import com.example.hubtrackerapp.presentation.screens.authorization.AuthorizationHubScreen
import com.example.hubtrackerapp.presentation.screens.authorization.RegistrationEnterEmailScreen
import com.example.hubtrackerapp.presentation.screens.home.HomeScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationChooseHabbitsScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationChoseGenderScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationScreen
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationViewModel

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
        navigation(
            route = Screen.RegistrationGraph.route,
            startDestination = Screen.RegistrationData.route
        ){
            composable(Screen.RegistrationData.route) {
                Log.d("Navigate","RegistrationScreen")

                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.RegistrationGraph.route)
                }
                val vm: RegistrationViewModel = hiltViewModel(parentEntry)


                RegistrationScreen(
                    viewModel = vm,
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
                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.RegistrationGraph.route)
                }
                val vm: RegistrationViewModel = hiltViewModel(parentEntry)

                RegistrationChoseGenderScreen(
                    viewModel = vm,
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
                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.RegistrationGraph.route)
                }
                val vm: RegistrationViewModel = hiltViewModel(parentEntry)

                RegistrationChooseHabbitsScreen(
                    viewModel = vm,
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

                val parentEntry = remember(it) {
                    navController.getBackStackEntry(Screen.RegistrationGraph.route)
                }
                val vm: RegistrationViewModel = hiltViewModel(parentEntry)

                RegistrationEnterEmailScreen(
                    viewModel = vm,
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
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                onAddHabitClick = {data ->
                    // data == null - создаем новый habit
                    // data != null - есть данные значить editMode
                    val route = if (data == null) {
                        Screen.AddHabit.createRoute()
                    } else {
                        Screen.AddHabit.createEditRoute(data)
                    }
                    navController.navigate(route)
                }
            )
        }
        composable(Screen.AddHabit.route) {
            val editData = Screen.AddHabit.getHabitData(it.arguments)
            AddHabit(
                editData = editData,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {

    data object Authorization : Screen("auth")

    data object RegistrationGraph : Screen("registration_graph")
    data object RegistrationEmail : Screen("reg_email")
    data object RegistrationData : Screen("reg_data")
    data object RegistrationGender : Screen("reg_gender")
    data object RegistrationHabits : Screen("reg_habits")
    data object HomeScreen: Screen("home")
    data object AddHabit: Screen("add_habit/{edit_data}"){

        fun createRoute(): String = "add_habit"
        fun createEditRoute(data: String): String = "add_habit/$data"

        fun getHabitData(arguments: Bundle?): String? {
            return arguments?.getString("edit_data")
        }
    }

}