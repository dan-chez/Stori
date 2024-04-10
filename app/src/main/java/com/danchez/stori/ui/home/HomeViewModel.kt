package com.danchez.stori.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danchez.stori.domain.usecases.GetTransactionsUseCase
import com.danchez.stori.ui.home.HomeUIState.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val uiMapper: TransactionsModelToUIModelMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    var transactions by mutableStateOf<List<TransactionUIModel>>(emptyList())
        private set

    fun getTransactions() {
        viewModelScope.launch {
            showLoading()
            getTransactionsUseCase()
                .onSuccess { transactionsModel ->
                    transactions = uiMapper.map(transactionsModel)
                    _uiState.update { currentState ->
                        currentState.copy(
                            state = UIState.Success,
                        )
                    }
                }
                .onFailure {
                    _uiState.update { currentState ->
                        currentState.copy(
                            state = UIState.Failure,
                        )
                    }
                }
        }
    }

    fun onTransactionTap(selectedTransaction: TransactionUIModel) {
        _uiState.update { currentState ->
            currentState.copy(
                state = UIState.ShowDetailsSheet(selectedTransaction),
            )
        }
    }

    fun onConfirmationDialogs() {
        _uiState.update { currentState ->
            currentState.copy(
                state = UIState.Initialized,
            )
        }
    }

    private fun showLoading() {
        _uiState.update { currentState ->
            currentState.copy(
                state = UIState.Loading,
            )
        }
    }
}
