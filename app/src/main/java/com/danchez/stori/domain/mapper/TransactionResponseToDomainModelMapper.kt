package com.danchez.stori.domain.mapper

import com.danchez.stori.data.model.TransactionResponse
import com.danchez.stori.domain.model.IncomeModel
import com.danchez.stori.domain.model.TransactionModel
import com.danchez.stori.domain.model.TransactionType
import com.danchez.stori.domain.model.WithdrawalModel
import javax.inject.Inject

interface TransactionResponseToDomainModelMapper {
    fun map(response: List<TransactionResponse>): List<TransactionModel>
}

class TransactionResponseToDomainModelMapperImpl @Inject constructor() : TransactionResponseToDomainModelMapper {

    override fun map(response: List<TransactionResponse>): List<TransactionModel> {
        val sortedList = response.sortedByDescending { it.timestamp ?: 0 }
        return sortedList.mapNotNull { transaction ->
            val transactionType = TransactionType.entries.firstOrNull {
                it.name == transaction.transactionType
            } ?: return@mapNotNull null
            return@mapNotNull when (transactionType) {
                TransactionType.WITHDRAWAL -> {
                    WithdrawalModel(
                        value = transaction.value.toString(),
                        description = transaction.description,
                        timestamp = transaction.timestamp,
                        destinationAccount = transaction.destinationAccount ?: "",
                    )
                }

                TransactionType.INCOME -> {
                    IncomeModel(
                        value = transaction.value.toString(),
                        description = transaction.description,
                        timestamp = transaction.timestamp,
                    )
                }
            }
        }
    }
}
