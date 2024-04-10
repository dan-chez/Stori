package com.danchez.stori.domain.usecases

import com.danchez.stori.data.TransactionsRepository
import com.danchez.stori.domain.model.AccountModel
import com.danchez.stori.domain.model.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CreateTransactionUseCase {
    suspend operator fun invoke(data: TransactionModel, account: AccountModel?): Result<Unit>
}

class CreateTransactionUseCaseImpl @Inject constructor(
    private val repository: TransactionsRepository,
    private val updateBalanceUseCase: UpdateBalanceUseCase,
) : CreateTransactionUseCase {
    override suspend fun invoke(data: TransactionModel, account: AccountModel?): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            if (account == null) {
                throw Exception("Account should not be null")
            }
            val mapData = data.toMap()
            repository.createTransaction(data = mapData)
            updateBalanceUseCase(
                balanceId = account.id,
                balance = account.balance,
                transactionValue = data.value.toLong(),
                transactionType = data.transactionType,
            )
        }.getOrThrow()
    }
}
