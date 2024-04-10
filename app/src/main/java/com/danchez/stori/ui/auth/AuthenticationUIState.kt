package com.danchez.stori.ui.auth

/**
 * Data class that represents the Authentication UI state
 */
data class AuthenticationUIState(
    val state: UIState = UIState.Initialized,
    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,
    val isButtonEnabled: Boolean = false,
) {
    sealed class UIState {
        data object Initialized : UIState()
        data object AuthenticationSuccess : UIState()
        data object AuthenticationFailure : UIState()
        data object Loading : UIState()
    }
}
