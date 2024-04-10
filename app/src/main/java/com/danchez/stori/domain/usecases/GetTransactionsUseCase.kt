package com.danchez.stori.domain.usecases

import com.danchez.stori.data.TransactionsRepository
import com.danchez.stori.domain.model.TransactionModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetTransactionsUseCase {
    suspend operator fun invoke(): Result<List<TransactionModel>>
}

class GetTransactionsUseCaseImpl @Inject constructor(
    private val repository: TransactionsRepository,
) : GetTransactionsUseCase {
    override suspend fun invoke(): Result<List<TransactionModel>> = runCatching {
        withContext(Dispatchers.IO) {
            repository.getTransactions()
        }.getOrThrow()
    }

}
