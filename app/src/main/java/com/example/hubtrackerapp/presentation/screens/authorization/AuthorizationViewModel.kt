package com.example.hubtrackerapp.presentation.screens.authorization

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.data.AuthRepositoryImpl
import com.example.hubtrackerapp.domain.auth.LoginUseCase
import kotlinx.coroutines.launch

class AuthorizationViewModel(context: Context) : ViewModel() {

    private val repository = AuthRepositoryImpl()
    private val loginUseCase = LoginUseCase(repository)
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set


    fun onEmailChanged(value: String){
        email = value
    }

    fun onPasswordChanged(value: String){
        password = value
    }

    fun onLoginClick(){
        viewModelScope.launch {
            val successLogin = loginUseCase(email, password)
            if (successLogin) {
                Log.d("AuthorizationViewModel","$successLogin - $email - $password")
            } else {
                Log.d("AuthorizationViewModel","$successLogin - $email - $password")
            }
        }
    }
}
