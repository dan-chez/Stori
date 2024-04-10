package com.danchez.stori.domain.mapper

import com.danchez.stori.data.model.AccountResponse
import com.danchez.stori.domain.model.AccountModel
import javax.inject.Inject

class AccountResponseToDomainModelMapper @Inject constructor(): Mapper<AccountResponse, AccountModel> {

    override fun map(input: AccountResponse): AccountModel {
        val id = input.id ?: ""
        val accountNumber = input.accountNumber ?: ""
        val balance = input.balance ?: 0
        return AccountModel(
            id = id,
            accountNumber = accountNumber,
            balance = balance,
        )
    }
}
