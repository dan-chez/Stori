package com.danchez.stori.ui.signup

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danchez.stori.domain.model.SignUpModel
import com.danchez.stori.domain.usecases.SignUpUseCase
import com.danchez.stori.ui.signup.SignUpUIState.UIState
import com.danchez.stori.utils.extensions.isEmailFormatValid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    private val _signUpState = MutableStateFlow(SignUpUIState())
    val signUpState: StateFlow<SignUpUIState> = _signUpState.asStateFlow()

    var name by mutableStateOf("")
        private set

    var userPhoto by mutableStateOf<Uri?>(null)
        private set

    var surname by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var confirmPassword by mutableStateOf("")
        private set

    fun signUp() = viewModelScope.launch {
        userPhoto?.let {
            showLoading()
            val model = SignUpModel(
                photoUri = it,
                name = name,
                surname = surname,
                email = email,
                password = password,
            )
            signUpUseCase(model = model)
                .onSuccess {
                    _signUpState.update { currentState ->
                        currentState.copy(
                            state = UIState.SignUpSuccess
                        )
                    }
                }
                .onFailure {
                    _signUpState.update { currentState ->
                        currentState.copy(
                            state = UIState.SignUpFailure
                        )
                    }
                }
        }
    }

    fun onChangePhoto(
        context: Context,
        cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>,
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    ) {
        val permissionCheckResult = validateCameraPermission(context)
        if (permissionCheckResult) {
            cameraLauncher.launch()
        } else {
            // Launches permission request
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun validateCameraPermission(context: Context): Boolean {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)

        return permissionCheckResult == PackageManager.PERMISSION_GRANTED
    }

    fun updateUserPhoto(photoUri: Uri) {
        this.userPhoto = photoUri
        checkButtonEnabled()
    }

    fun updateSurname(surname: String) {
        this.surname = surname
        checkButtonEnabled()
        _signUpState.update { currentState ->
            currentState.copy(
                showEmailError = surname.isEmpty(),
            )
        }
    }

    fun updateName(name: String) {
        this.name = name
        checkButtonEnabled()
        _signUpState.update { currentState ->
            currentState.copy(
                showEmailError = name.isEmpty(),
            )
        }
    }

    fun updateEmail(email: String) {
        this.email = email
        checkButtonEnabled()
        val emailValid = email.isEmailFormatValid()
        _signUpState.update { currentState ->
            currentState.copy(
                showEmailError = emailValid.not(),
            )
        }
    }

    fun updatePassword(password: String) {
        this.password = password
        checkButtonEnabled()
        val isPasswordValid = password.length >= 6
        validateConfirmPassword()
        _signUpState.update { currentState ->
            currentState.copy(
                showPasswordError = isPasswordValid.not(),
            )
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        this.confirmPassword = confirmPassword
        checkButtonEnabled()
        validateConfirmPassword()
    }

    fun hideDialogs() {
        _signUpState.update { currentState ->
            currentState.copy(
                state = UIState.Initialized,
            )
        }
    }

    private fun showLoading() {
        _signUpState.update { currentState ->
            currentState.copy(
                state = UIState.Loading,
            )
        }
    }

    private fun validateConfirmPassword() {
        _signUpState.update { currentState ->
            currentState.copy(
                showConfirmPasswordError = confirmPassword != password,
            )
        }
    }

    private fun checkButtonEnabled() {
        val isButtonEnabled = (userPhoto != null) && name.isNotBlank() && surname.isNotBlank() &&
                email.isEmailFormatValid() && password.isNotBlank() && (password == confirmPassword)
        _signUpState.update { currentState ->
            currentState.copy(
                isButtonEnabled = isButtonEnabled
            )
        }
    }
}
