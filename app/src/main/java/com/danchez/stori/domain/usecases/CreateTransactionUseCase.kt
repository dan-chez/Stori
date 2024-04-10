package com.danchez.stori.domain.usecases

import com.danchez.stori.data.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CreateTransactionUseCase {
    suspend operator fun invoke(data: Map<String, Any>): Result<Unit>
}

class CreateTransactionUseCaseImpl @Inject constructor(
    private val repository: TransactionsRepository,
) : CreateTransactionUseCase {
    override suspend fun invoke(data: Map<String, Any>): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            repository.createTransaction(data = data)
        }.getOrThrow()
    }
}
