package com.danchez.stori.domain.model

sealed class TransactionModel {
    abstract val description: String?
    abstract val timestamp: Long?
    abstract val value: String
}

data class WithdrawalModel(
    override val value: String,
    override val description: String?,
    override val timestamp: Long?,
    val destinationAccount: String,
) : TransactionModel()

data class IncomeModel(
    override val value: String,
    override val description: String?,
    override val timestamp: Long?,
) : TransactionModel()
