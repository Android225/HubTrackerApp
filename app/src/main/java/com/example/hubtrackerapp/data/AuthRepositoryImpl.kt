package com.example.hubtrackerapp.data

import android.util.Log
import com.example.hubtrackerapp.domain.auth.AuthRepository
import com.example.hubtrackerapp.domain.user.User
import com.example.hubtrackerapp.presentation.screens.registration.RegistrationHabitsState
import com.example.hubtrackerapp.presentation.screens.registration.model.HabitUi
import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update

object AuthRepositoryImpl : AuthRepository {

    private val _testHubb = MutableStateFlow(
        RegistrationHabitsState(
            habits = listOf(
                HabitUi(1, "💧", "Drink water"),
                HabitUi(2, "🏃‍♀️", "Run"),
                HabitUi(3, "📖", "Read books"),
                HabitUi(4, "🧘‍♀️", "Meditate"),
                HabitUi(5, "🧑‍💻", "Study"),
                HabitUi(6, "📕", "Journal"),
                HabitUi(7, "🌿", "Water plant"),
                HabitUi(8, "😴", "Sleep"),
            )
        )
    )
    val testHubb = _testHubb.asStateFlow()


    private val testUsers = mutableListOf<User>().apply {
        repeat(10){
            add(User(
                userId = it,
                email = "$it@hub.com",
                password = "$it",
                firstName = "$it-FirstName",
                lastName = "$it-LastName",
                birthDate = "$it/$it/$it",
                gender = if (it % 2 == 0) "Male" else "Female",
                habbies = testHubb.value.habits
            ))
        }
    }

    private val usersListFlow = MutableStateFlow<List<User>>(testUsers)


    override fun login(email: String, password: String): Boolean {
        return email == "Admin@Admin" && password == "Admin"
    }

    override fun register(
        registerUser: RegistrationDraft
    ): Boolean {

        val user = User(
            userId = usersListFlow.value.size,
            email = registerUser.email,
            password = registerUser.password,
            firstName = registerUser.firstName,
            lastName = registerUser.lastName,
            birthDate = registerUser.birthDate,
            gender = registerUser.gender,
            habbies = registerUser.habbies
        )

        val exist = usersListFlow.value.any{it.email == user.email}

        if (exist){
            return false
        }

        usersListFlow.update { oldList ->

            oldList + user
        }
        Log.d("Register","REPOSITORE ${usersListFlow.value.last().email} - ${usersListFlow.value.last().email}")
        return true
    }

}