package com.danchez.stori.ui.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.danchez.stori.ui.home.AccountUIModel

class AccountInfoDataProvider : PreviewParameterProvider<AccountUIModel> {
    override val values: Sequence<AccountUIModel> = sequenceOf(
        getModel()
    )

    private fun getModel(): AccountUIModel {
        return AccountUIModel(
            account = "1234-5678-9012-1234",
            totalBalance = "\$2.000.000,00",
        )
    }

}
