package com.danchez.stori.ui.home

import android.net.Uri

/**
 * Data class that represents the Home UI state
 */
data class HomeUIState(
    val state: UIState = UIState.Initialized,
    val displayName: String = "",
    val photoUrl: Uri? = null,
) {
    sealed class UIState {
        data object Initialized : UIState()
        data object Success : UIState()
        data object Failure : UIState()
        data class ShowDetailsSheet(val transactionUIModel: TransactionUIModel) : UIState()
        data object Logout : UIState()
        data object Loading : UIState()
    }
}