package com.example.hubtrackerapp.presentation.screens.authorization

//import com.example.hubtrackerapp.data.AuthRepositoryImpl
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hubtrackerapp.domain.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val loginUseCase:LoginUseCase
) : ViewModel() {


    //private val repository = AuthRepositoryImpl
   // private val loginUseCase = LoginUseCase(repository)

    private val _state = MutableStateFlow(AuthData())
    val state = _state.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess = _loginSuccess.asStateFlow()
    fun onAuthEvent(event: AuthorizationEvent){
        when(event){
            AuthorizationEvent.ForgetPassword -> {
                TODO()
            }
            AuthorizationEvent.Login -> {
                Log.d("Auth","onLoginClick")
                viewModelScope.launch {
                    Log.d("Auth","ScopeDoneLaunch")
                    val successLogin = loginUseCase(_state.value.email, _state.value.password)
                    if (successLogin) {
                        Log.d("Auth","$successLogin - ${_state.value.email} - ${_state.value.password}")
                    } else {
                        Log.d("Auth","$successLogin - ${_state.value.email} - ${_state.value.password}")
                    }
                    _loginSuccess.value = successLogin
                }
            }
            is AuthorizationEvent.OnEmailEnter -> {
                Log.d("Auth", "email enter - > ${event.email}")
                _state.update {
                    it.copy(email = event.email)
                }
            }
            is AuthorizationEvent.OnPasswordEnter -> {
                Log.d("Auth", "email enter - > ${event.password}")
                _state.update {
                    it.copy(password = event.password)
                }
            }
            AuthorizationEvent.RegisterAccount -> {
                Log.d("Auth","Go to Register new Account")
            }
        }
    }

}
data class AuthData(
    var email: String = "",
    var password : String = ""
)


sealed interface AuthorizationEvent{
    data object Login: AuthorizationEvent
    data object ForgetPassword: AuthorizationEvent
    data object RegisterAccount: AuthorizationEvent
    data class OnPasswordEnter(val password: String): AuthorizationEvent
    data class OnEmailEnter(val email: String): AuthorizationEvent
}
