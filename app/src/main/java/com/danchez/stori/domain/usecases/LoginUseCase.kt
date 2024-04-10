package com.danchez.stori.domain.usecases

import com.danchez.stori.data.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Result<Unit>
}

class LoginUseCaseImpl @Inject constructor(
    private val repository: AuthRepository,
) : LoginUseCase {
    override suspend fun invoke(email: String, password: String): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            repository.login(email = email, password = password)
        }.getOrThrow()
    }
}
