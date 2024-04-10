package com.danchez.stori.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danchez.stori.data.AuthRepository
import com.danchez.stori.domain.AccountMediator
import com.danchez.stori.domain.usecases.GetAccountUseCase
import com.danchez.stori.domain.usecases.GetTransactionsUseCase
import com.danchez.stori.ui.home.HomeUIState.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val transactionsUIMapper: TransactionsModelToUIModelMapper,
    private val accountUIMapper: AccountModelToUIModelMapper,
    private val accountMediator: AccountMediator,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()

    var transactions by mutableStateOf<List<TransactionUIModel>>(emptyList())
        private set

    var account by mutableStateOf(AccountUIModel.initial())
        private set

    fun getHomeData() {
        showLoading()
        viewModelScope.launch {
            authRepository.currentUser?.let {
                _uiState.update { currentState ->
                    currentState.copy(
                        displayName = it.displayName ?: "",
                        photoUrl = it.photoUrl
                    )
                }
            }
            val getTransactions = async { getTransactions() }
            val getAccount = async { getAccount() }

            getTransactions.await()
            getAccount.await()

        }
    }

    private suspend fun getTransactions() {
        getTransactionsUseCase()
            .onSuccess { transactionsModel ->
                transactions = transactionsUIMapper.map(transactionsModel)
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

    private suspend fun getAccount() {
        getAccountUseCase()
            .onSuccess { accountModel ->
                accountMediator.account = accountModel
                account = accountUIMapper.map(accountModel)
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

    fun onLogoutTap() {
        authRepository.logout()
        _uiState.update { currentState ->
            currentState.copy(
                state = UIState.Logout,
            )
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
