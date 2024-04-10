package com.danchez.stori.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.danchez.stori.domain.model.AccountModel
import javax.inject.Inject

class AccountMediator @Inject constructor() {

    var account by mutableStateOf<AccountModel?>(null)
}
