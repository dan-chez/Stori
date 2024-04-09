package com.danchez.stori.ui.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.danchez.stori.ui.home.AccountModel

class AccountInfoDataProvider : PreviewParameterProvider<AccountModel> {
    override val values: Sequence<AccountModel> = sequenceOf(
        getModel()
    )

    private fun getModel(): AccountModel {
        return AccountModel(
            account = "1234 5678 9012",
            totalBalance = "\$2.000.000,00",
        )
    }

}
