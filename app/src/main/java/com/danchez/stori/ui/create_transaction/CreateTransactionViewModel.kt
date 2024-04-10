package com.danchez.stori.ui.create_transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danchez.stori.domain.AccountMediator
import com.danchez.stori.domain.model.IncomeModel
import com.danchez.stori.domain.model.TransactionModel
import com.danchez.stori.domain.model.TransactionType
import com.danchez.stori.domain.model.WithdrawalModel
import com.danchez.stori.domain.usecases.CreateTransactionUseCase
import com.danchez.stori.ui.create_transaction.CreateTransactionUIState.UIState
import com.danchez.stori.utils.TransformedTextUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTransactionViewModel @Inject constructor(
    private val createTransactionUseCase: CreateTransactionUseCase,
    private val accountMediator: AccountMediator,
) : ViewModel() {

    private val _uIState = MutableStateFlow(CreateTransactionUIState())
    val uIState: StateFlow<CreateTransactionUIState> = _uIState.asStateFlow()

    var value by mutableStateOf("")
        private set

    var destinationAccount by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var transactionTypeSelected by mutableStateOf(TransactionType.WITHDRAWAL)
        private set

    fun createTransaction() {
        val data = createData()
        viewModelScope.launch {
            showLoading()
            createTransactionUseCase(data = data, account = accountMediator.account)
                .onSuccess {
                    _uIState.update { currentState ->
                        currentState.copy(
                            state = UIState.Success
                        )
                    }
                }
                .onFailure {
                    _uIState.update { currentState ->
                        currentState.copy(
                            state = UIState.Failure
                        )
                    }
                }
        }
    }

    fun updateTransactionTypeSelected(transactionType: TransactionType) {
        this.transactionTypeSelected = transactionType
    }

    fun updateValue(value: String) {
        this.value = value
        checkButtonEnabled()
        _uIState.update { currentState ->
            currentState.copy(
                showValueError = value.isBlank(),
            )
        }
    }

    fun updateDestinationAccount(destinationAccount: String) {
        if (destinationAccount.length <= TransformedTextUtils.MAX_CARD_LENGTH) {
            this.destinationAccount = destinationAccount
            val isDestinationAccountValid = destinationAccount.length == TransformedTextUtils.MAX_CARD_LENGTH
            checkButtonEnabled()
            _uIState.update { currentState ->
                currentState.copy(
                    showDestinationAccountError = isDestinationAccountValid.not()
                )
            }
        }
    }

    fun updateDescription(description: String) {
        this.description = description
        checkButtonEnabled()
    }

    fun hideDialogs() {
        _uIState.update { currentState ->
            currentState.copy(
                state = UIState.Initialized,
            )
        }
    }

    private fun showLoading() {
        _uIState.update { currentState ->
            currentState.copy(
                state = UIState.Loading,
            )
        }
    }

    private fun checkButtonEnabled() {
        val isButtonEnabled = if (transactionTypeSelected == TransactionType.WITHDRAWAL) {
            value.isNotBlank() && (destinationAccount.length == TransformedTextUtils.MAX_CARD_LENGTH)
        } else {
            value.isNotBlank()
        }
        _uIState.update { currentState ->
            currentState.copy(
                isButtonEnabled = isButtonEnabled
            )
        }
    }

    private fun createData(): TransactionModel {
        val currentTimestamp = System.currentTimeMillis()
        return when (transactionTypeSelected) {
            TransactionType.WITHDRAWAL -> {
                WithdrawalModel(
                    value = value,
                    description = description,
                    timestamp = currentTimestamp,
                    transactionType = transactionTypeSelected,
                    destinationAccount = destinationAccount,
                )
            }

            TransactionType.INCOME -> {
                IncomeModel(
                    value = value,
                    description = description,
                    timestamp = currentTimestamp,
                    transactionType = transactionTypeSelected,
                )
            }
        }
    }
}
