package com.danchez.stori.ui.home

import com.danchez.stori.domain.model.AccountModel
import com.danchez.stori.utils.extensions.formatToCard
import com.danchez.stori.utils.extensions.formatToMoney
import javax.inject.Inject

class AccountModelToUIModelMapper @Inject constructor() {

    fun map(input: AccountModel): AccountUIModel {
        val account = input.accountNumber.formatToCard()
        val totalBalance = input.balance.toString().formatToMoney()
        return AccountUIModel(
            account = account,
            totalBalance = totalBalance,
        )
    }
}
