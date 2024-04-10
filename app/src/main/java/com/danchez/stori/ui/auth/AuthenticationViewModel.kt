package com.danchez.stori.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danchez.stori.domain.usecases.LoginUseCase
import com.danchez.stori.ui.auth.AuthenticationUIState.UIState
import com.danchez.stori.utils.extensions.isEmailFormatValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthenticationUIState())
    val authState: StateFlow<AuthenticationUIState> = _authState.asStateFlow()

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun updateEmail(email: String) {
        this.email = email
        checkButtonEnabled()
        val emailValid = email.isEmailFormatValid()
        _authState.update { currentState ->
            currentState.copy(
                showEmailError = emailValid.not(),
            )
        }
    }

    fun updatePassword(password: String) {
        this.password = password
        checkButtonEnabled()
        val isPasswordValid = password.length >= 6
        _authState.update { currentState ->
            currentState.copy(
                showPasswordError = isPasswordValid.not(),
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            showLoading()
            loginUseCase(email = email, password = password)
                .onSuccess {
                    _authState.update { currentState ->
                        currentState.copy(
                            state = UIState.AuthenticationSuccess,
                        )
                    }
                }
                .onFailure {
                    _authState.update { currentState ->
                        currentState.copy(
                            state = UIState.AuthenticationFailure,
                        )
                    }
                }
        }
    }

    fun hideDialogs() {
        _authState.update { currentState ->
            currentState.copy(
                state = UIState.Initialized,
            )
        }
    }

    private fun showLoading() {
        _authState.update { currentState ->
            currentState.copy(
                state = UIState.Loading,
            )
        }
    }

    private fun checkButtonEnabled() {
        val emailValid = email.isEmailFormatValid()
        _authState.update { currentState ->
            currentState.copy(
                isButtonEnabled = emailValid && password.isNotBlank()
            )
        }
    }
}
