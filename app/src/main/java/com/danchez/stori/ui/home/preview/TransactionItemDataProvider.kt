package com.danchez.stori.ui.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.danchez.stori.ui.home.TransactionModel
import com.danchez.stori.ui.home.TransactionType
import java.util.Date

class TransactionItemDataProvider : PreviewParameterProvider<TransactionModel> {
    override val values: Sequence<TransactionModel> = sequenceOf(
        getModel()
    )

    private fun getModel(): TransactionModel {
        return TransactionModel(
            Date(),
            "$500.000,00",
            "Apartment fee",
            TransactionType.WITHDRAWAL,
        )
    }
}
