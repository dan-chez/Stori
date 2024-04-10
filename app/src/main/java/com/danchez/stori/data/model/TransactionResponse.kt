package com.danchez.stori.data.model

data class TransactionResponse(
    val description: String? = null,
    val destinationAccount: String? = null,
    val timestamp: Long? = null,
    val transactionType: String? = null,
    val value: Int? = null,
)
