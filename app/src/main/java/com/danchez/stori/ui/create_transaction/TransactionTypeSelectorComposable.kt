package com.danchez.stori.ui.create_transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.danchez.stori.R
import com.danchez.stori.domain.model.TransactionType

@Composable
fun TransactionTypeSelectorComposable(
    transactionTypeSelected: TransactionType,
    onOptionSelected: (TransactionType) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TransactionType.entries.forEach { option ->
            val text = when (option) {
                TransactionType.INCOME -> stringResource(id = R.string.income)
                TransactionType.WITHDRAWAL -> stringResource(id = R.string.withdrawal)
            }
            Row(
                Modifier
                    .selectable(
                        selected = (option == transactionTypeSelected),
                        onClick = {
                            onOptionSelected(option)
                        }
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (option == transactionTypeSelected),
                    onClick = { onOptionSelected(option) }
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.merge(),
                )
            }
        }
    }
}
