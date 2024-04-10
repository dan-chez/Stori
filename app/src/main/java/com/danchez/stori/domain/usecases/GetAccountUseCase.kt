package com.danchez.stori.domain.usecases

import com.danchez.stori.data.AccountRepository
import com.danchez.stori.domain.model.AccountModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetAccountUseCase {
    suspend operator fun invoke(): Result<AccountModel>
}

class GetAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository,
): GetAccountUseCase {
    override suspend fun invoke(): Result<AccountModel> = runCatching {
        withContext(Dispatchers.IO) {
            accountRepository.getAccount()
        }.getOrThrow()
    }

}
