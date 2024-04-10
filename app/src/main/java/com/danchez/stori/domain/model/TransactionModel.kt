package com.danchez.stori.domain.model

sealed class TransactionModel {
    abstract val description: String?
    abstract val timestamp: Long?
    abstract val value: String
    abstract val transactionType: TransactionType

    open fun toMap(): Map<String, Any?> {
        return mapOf<String, Any?>(
            "description" to description,
            "timestamp" to timestamp,
            "value" to value.toLong(),
            "transactionType" to transactionType,
        )
    }
}

data class WithdrawalModel(
    override val value: String,
    override val description: String?,
    override val timestamp: Long?,
    override val transactionType: TransactionType,
    val destinationAccount: String,
) : TransactionModel() {

    override fun toMap(): Map<String, Any?> {
        val superMap = super.toMap()
        return superMap + mapOf("destinationAccount" to destinationAccount)
    }
}

data class IncomeModel(
    override val value: String,
    override val description: String?,
    override val timestamp: Long?,
    override val transactionType: TransactionType,
) : TransactionModel()
