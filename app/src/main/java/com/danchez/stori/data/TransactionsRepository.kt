package com.danchez.stori.data

import android.util.Log
import com.danchez.stori.data.model.TransactionResponse
import com.danchez.stori.domain.mapper.TransactionResponseToDomainModelMapper
import com.danchez.stori.domain.model.TransactionModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface TransactionsRepository {
    suspend fun createTransaction(data: Map<String, Any>): Result<Unit>

    suspend fun getTransactions(): Result<List<TransactionModel>>
}

class TransactionsRepositoryImpl @Inject constructor(
    private val repository: AuthRepository,
    private val firestore: FirebaseFirestore,
    private val mapper: TransactionResponseToDomainModelMapper,
) : TransactionsRepository {
    override suspend fun createTransaction(data: Map<String, Any>): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val currentUser = repository.currentUser
                currentUser?.let { user ->
                    firestore.collection(DataConstants.MAIN_COLLECTION).document(user.uid).collection("transactions")
                        .add(data)
                        .addOnSuccessListener { _ ->
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

    override suspend fun getTransactions(): Result<List<TransactionModel>> {
        return suspendCancellableCoroutine { continuation ->
            try {
                val currentUser = repository.currentUser
                currentUser?.let { user ->
                    firestore.collection(DataConstants.MAIN_COLLECTION).document(user.uid).collection("transactions")
                        .get()
                        .addOnSuccessListener { result ->
                            val transactionsResponse = result.map { document ->
                                document.toObject<TransactionResponse>()
                            }
                            val transactions = mapper.map(transactionsResponse)
                            continuation.resume(Result.success(transactions))
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
}
