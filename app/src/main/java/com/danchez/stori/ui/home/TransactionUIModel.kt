package com.danchez.stori.ui.home

sealed class TransactionUIModel {
    abstract val date: String
    abstract val value: String
    abstract val description: String?
}

data class IncomeUIModel(
    override val date: String,
    override val value: String,
    override val description: String?,
) : TransactionUIModel()

data class WithdrawalUIModel(
    override val date: String,
    override val value: String,
    override val description: String?,
    val destinationAccount: String,
) : TransactionUIModel()
