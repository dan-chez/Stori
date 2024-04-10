package com.danchez.stori.domain.usecases

import com.danchez.stori.data.AccountRepository
import com.danchez.stori.domain.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UpdateBalanceUseCase {
    suspend operator fun invoke(
        balanceId: String,
        balance: Long,
        transactionValue: Long,
        transactionType: TransactionType,
    ): Result<Unit>
}

class UpdateBalanceUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
) : UpdateBalanceUseCase {
    override suspend fun invoke(
        balanceId: String,
        balance: Long,
        transactionValue: Long,
        transactionType: TransactionType,
    ): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            val newBalance = when (transactionType) {
                TransactionType.WITHDRAWAL -> {
                    balance - transactionValue
                }
                TransactionType.INCOME -> {
                    balance + transactionValue
                }
            }
            accountRepository.updateBalance(balanceId = balanceId, balance = newBalance)
        }.getOrThrow()
    }

}
