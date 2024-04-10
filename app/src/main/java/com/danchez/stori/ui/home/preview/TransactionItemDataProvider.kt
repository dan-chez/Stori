package com.danchez.stori.ui.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.danchez.stori.ui.home.IncomeUIModel
import com.danchez.stori.ui.home.TransactionUIModel
import com.danchez.stori.ui.home.WithdrawalUIModel

class TransactionItemDataProvider : PreviewParameterProvider<TransactionUIModel> {
    override val values: Sequence<TransactionUIModel> = sequenceOf(
        getModel()
    )

    private fun getModel(): TransactionUIModel {
        return WithdrawalUIModel(
            "MMM dd, yyyy",
            "$500.000,00",
            "Apartment fee asdasdas dasdasd asdas dasdas das dsadasdas",
            "12325152352525252"
        )
    }
}
