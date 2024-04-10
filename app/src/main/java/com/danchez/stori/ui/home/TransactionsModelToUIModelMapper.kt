package com.danchez.stori.ui.home

import com.danchez.stori.domain.model.IncomeModel
import com.danchez.stori.domain.model.TransactionModel
import com.danchez.stori.domain.model.WithdrawalModel
import com.danchez.stori.utils.extensions.formatDateToString
import com.danchez.stori.utils.extensions.formatToMoney
import java.util.Date
import javax.inject.Inject

class TransactionsModelToUIModelMapper @Inject constructor() {

    fun map(input: List<TransactionModel>): List<TransactionUIModel> {
        return input.map { model ->
            when (model) {
                is WithdrawalModel -> {
                    WithdrawalUIModel(
                        date = model.timestamp?.let { Date(it).formatDateToString() } ?: "",
                        value = model.value.formatToMoney(),
                        description = model.description,
                        destinationAccount = model.destinationAccount,
                    )
                }

                is IncomeModel -> {
                    IncomeUIModel(
                        date = model.timestamp?.let { Date(it).formatDateToString() } ?: "",
                        value = model.value.formatToMoney(),
                        description = model.description,
                    )
                }
            }
        }
    }
}
