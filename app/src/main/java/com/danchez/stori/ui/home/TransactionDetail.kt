package com.danchez.stori.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.danchez.stori.R
import com.danchez.stori.ui.common.IncomeIcon
import com.danchez.stori.ui.common.WithdrawalIcon
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetail(
    sheetState: SheetState,
    transactionUIModel: TransactionUIModel,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        TransactionDetailContent(
            transactionUIModel = transactionUIModel,
        )
    }
}

@Composable
private fun TransactionDetailContent(
    modifier: Modifier = Modifier,
    transactionUIModel: TransactionUIModel,
) {
    val spacing = MaterialTheme.spacing
    Column(
        modifier = modifier
            .padding(
                start = spacing.large,
                end = spacing.large,
                bottom = spacing.extraLarge,
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.transaction_details),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
        Spacer(modifier = Modifier.height(spacing.large))
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(id = R.string.value),
            )
            Spacer(modifier = Modifier.width(spacing.large))
            when (transactionUIModel) {
                is WithdrawalUIModel -> WithdrawalIcon()
                is IncomeUIModel -> IncomeIcon()
            }
        }
        Text(
            text = transactionUIModel.value,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (!transactionUIModel.description.isNullOrBlank()) {
                Column(
                    modifier = Modifier.weight(weight = 1f),
                ) {
                    Text(
                        text = stringResource(id = R.string.description),
                    )
                    Text(
                        text = transactionUIModel.description!!,
                    )
                }
            }
            Column(
                modifier = Modifier.weight(weight = 0.5f).align(alignment = Alignment.Bottom),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = stringResource(id = R.string.date),
                )
                Text(
                    text = transactionUIModel.date,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDetailContentPreview() {
    StoriTheme {
        TransactionDetailContent(
            transactionUIModel = WithdrawalUIModel(
                "",
                "$500.000,00",
                "Apartment fee",
                ""
            )
        )
    }
}
