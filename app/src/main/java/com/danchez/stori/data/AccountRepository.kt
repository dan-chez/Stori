package com.danchez.stori.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface AccountRepository {
    suspend fun createAccount(data: Map<String, Any>): Result<Unit>
}

class AccountRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val firestore: FirebaseFirestore,
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

    companion object {
        private const val ACCOUNT_COLLECTION = "account"
        private const val TAG = "AccountRepository"
    }
}
