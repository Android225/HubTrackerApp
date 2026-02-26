//package com.example.hubtrackerapp.data
//
//import android.util.Log
//import androidx.compose.ui.graphics.Color
//import com.example.hubtrackerapp.data.TempDB.habitsStateFlow
//import com.example.hubtrackerapp.data.TempDB.testUser
//import com.example.hubtrackerapp.data.TempDB.usersListFlow
//import com.example.hubtrackerapp.domain.auth.AuthRepository
//import com.example.hubtrackerapp.domain.hubbit.models.HabitSchedule
//import com.example.hubtrackerapp.domain.user.User
//import com.example.hubtrackerapp.domain.hubbit.models.HabitUi
//import com.example.hubtrackerapp.domain.hubbit.models.ModeForSwitchInHabit
//import com.example.hubtrackerapp.presentation.screens.registration.model.RegistrationDraft
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.update
//import java.time.LocalDate
//import java.time.LocalTime
//import java.util.UUID
//
//object AuthRepositoryImpl : AuthRepository {
//
//
//    override suspend fun login(email: String, password: String): Boolean {
//        Log.d("Auth","--- START LOGIN ---")
//        val exist = usersListFlow.value.any { it.email == email && it.password == password}
//        Log.d("Auth","Exist - $exist")
//        if (exist){
//            testUser = usersListFlow.value.find { it.email == email && it.password == password }?: return false
//        }
//        return exist
//    }
//
//    override suspend fun register(
//        registerUser: RegistrationDraft
//    ): Boolean {
//        Log.d("Register", "--- REGISTER START ---")
//        Log.d("Register", "New user email: ${registerUser.email}")
//
//        Log.d("Register", "Existing users count: ${usersListFlow.value.size}")
//        usersListFlow.value.forEachIndexed { index, user ->
//            Log.d("Register", "User $index: ${user.email} (compare with: ${registerUser.email})")
//            Log.d("Register", "Email equality check: ${user.email == registerUser.email}")
//        }
//
//        val existIgnoreCase = usersListFlow.value.any {
//            it.email.equals(registerUser.email, ignoreCase = true)
//        }
//        Log.d("Register", "Exist (ignoreCase): $existIgnoreCase")
//
//        val exist: Boolean = usersListFlow.value.any { it.email == registerUser.email }
//        Log.d("Register", "Exist (exact match): $exist")
//
//        if (exist) {
//            Log.d("Register", "Registration FAILED - user already exists")
//            return false
//        }
//
//        val user = User(
//            userId = UUID.randomUUID().toString(),
//            email = registerUser.email,
//            password = registerUser.password,
//            firstName = registerUser.firstName,
//            lastName = registerUser.lastName,
//            birthDate = registerUser.birthDate,
//            gender = registerUser.gender
//        )
//
//        val habbitsList = registerUser.habbies.map { registerHabit ->
//            HabitUi(
//                habitId = UUID.randomUUID().toString(),
//                userId = user.userId,
//                emoji = registerHabit.icon,
//                title = registerHabit.habitName,
//                createdAt = LocalDate.now(),
//                schedule = registerHabit.habitSchedule,
//                color = registerHabit.color,
//                target = registerHabit.target,
//                metric = registerHabit.metricForHabit,
//                reminderTime = registerHabit.reminderTime,
//                reminderDate = registerHabit.reminderDate,
//                habitType = registerHabit.habitType,
//            )
//        }
//
//        usersListFlow.update { oldList ->
//            Log.d("Register","User added for base")
//            oldList + user
//        }
//        habitsStateFlow.update { oldList ->
//            Log.d("Register","predefined Habits Added")
//            oldList + habbitsList
//        }
//        Log.d(
//            "Register",
//            "REPOSITORE ${usersListFlow.value.last().email} - ${usersListFlow.value.last().email}"
//        )
//        Log.d(
//            "Register",
//            "REPOSITOREDataToADd ${user} - ${habbitsList}"
//        )
//        return true
//    }
//
//}
//
