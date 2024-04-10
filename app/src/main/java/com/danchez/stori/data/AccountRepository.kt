package com.danchez.stori.data

import android.util.Log
import com.danchez.stori.data.model.AccountResponse
import com.danchez.stori.data.model.TransactionResponse
import com.danchez.stori.domain.mapper.AccountResponseToDomainModelMapper
import com.danchez.stori.domain.model.AccountModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface AccountRepository {
    suspend fun createAccount(data: Map<String, Any>): Result<Unit>
    suspend fun getAccount(): Result<AccountModel>
}

class AccountRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore,
    private val mapper: AccountResponseToDomainModelMapper,
) : AccountRepository {

    override suspend fun createAccount(data: Map<String, Any>): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val currentUser = authRepository.currentUser
                currentUser?.let { user ->
                    firestore.collection(DataConstants.MAIN_COLLECTION).document(user.uid)
                        .collection(ACCOUNT_COLLECTION)
                        .add(data)
                        .addOnSuccessListener { _ ->
                            Log.i(TAG, "Account created.")
                            continuation.resume(Result.success(Unit))
                        }
                        .addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
                } ?: continuation.resumeWithException(Exception("CurrentUser is null"))
            } catch (e: Exception) {
                e.printStackTrace()
                continuation.resumeWithException(e)
            }
        }
    }

    override suspend fun getAccount(): Result<AccountModel> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val currentUser = authRepository.currentUser
                currentUser?.let { user ->
                    firestore.collection(DataConstants.MAIN_COLLECTION).document(user.uid)
                        .collection(ACCOUNT_COLLECTION)
                        .get()
                        .addOnSuccessListener { result ->
                            val accountResponse = result.firstOrNull()
                            if (accountResponse == null) {
                                Log.i(TAG, "Account is null.")
                                continuation.resumeWithException(Exception("Account is null"))
                                return@addOnSuccessListener
                            }
                            val account = accountResponse.toObject<AccountResponse>().let {
                                mapper.map(it)
                            }
                            continuation.resume(Result.success(account))
                        }
                        .addOnFailureListener { e ->
                            continuation.resumeWithException(e)
                        }
                } ?: continuation.resumeWithException(Exception("CurrentUser is null"))
            } catch (e: Exception) {
                e.printStackTrace()
                continuation.resumeWithException(e)
            }
        }
    }

    companion object {
        private const val ACCOUNT_COLLECTION = "account"
        private const val TAG = "AccountRepository"
    }
}
