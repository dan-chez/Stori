package com.danchez.stori.ui.create_transaction

/**
 * Data class that represents the CreateTransaction UI state
 */
data class CreateTransactionUIState(
    val state: UIState = UIState.Initialized,
    val isButtonEnabled: Boolean = false,
    val showValueError: Boolean = false,
    val showDestinationAccountError: Boolean = false,
) {
    sealed class UIState {
        data object Initialized : UIState()
        data object Success : UIState()
        data object Failure : UIState()
        data object FoundsError : UIState()
        data object Loading : UIState()
    }
}
