package com.danchez.stori.domain.usecases

import com.danchez.stori.data.AuthRepository
import com.danchez.stori.domain.model.SignUpModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SignUpUseCase {
    suspend operator fun invoke(model: SignUpModel): Result<Unit>
}

class SignUpUseCaseImpl @Inject constructor(
    private val repository: AuthRepository,
) : SignUpUseCase {
    override suspend fun invoke(model: SignUpModel): Result<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            val fullName = "${model.name.trim()} ${model.surname.trim()}"
            repository.signup(
                photoUri = model.photoUri,
                fullName = fullName,
                email = model.email,
                password = model.password,
            )
        }.getOrThrow()
    }
}
