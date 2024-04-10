package com.danchez.stori.ui.home

/**
 * Data class that represents the Home UI state
 */
data class HomeUIState(
    val state: UIState = UIState.Initialized,
) {
    sealed class UIState {
        data object Initialized : UIState()
        data object Success : UIState()
        data object Failure : UIState()
        data class ShowDetailsSheet(val transactionUIModel: TransactionUIModel) : UIState()
        data object Loading : UIState()
    }
}