package com.danchez.stori.ui.signup

/**
 * Data class that represents the SignUp UI state
 */
data class SignUpUIState(
    val state: UIState = UIState.Initialized,
    val isButtonEnabled: Boolean = false,
    val showNameError: Boolean = false,
    val showSurnameError: Boolean = false,
    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,
    val showConfirmPasswordError: Boolean = false,
) {
    sealed class UIState {
        data object Initialized : UIState()
        data object SignUpSuccess : UIState()
        data object SignUpFailure : UIState()
        data object Loading : UIState()
    }
}
