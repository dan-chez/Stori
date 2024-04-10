package com.danchez.stori.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface TransactionsRepository {
    suspend fun createTransaction(data: Map<String, Any>): Result<Unit>
}

class TransactionsRepositoryImpl @Inject constructor(
    private val repository: AuthRepository,
    private val firestore: FirebaseFirestore,
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
}
