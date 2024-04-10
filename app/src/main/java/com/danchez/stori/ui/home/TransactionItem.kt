package com.danchez.stori.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.danchez.stori.R
import com.danchez.stori.ui.common.ChevronRightIcon
import com.danchez.stori.ui.common.DepositIcon
import com.danchez.stori.ui.common.WithdrawalIcon
import com.danchez.stori.ui.home.preview.TransactionItemDataProvider
import com.danchez.stori.ui.theme.Spacing
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.utils.extensions.formatDateToString
import java.util.Date

@Composable
fun TransactionItem(
    model: TransactionModel,
    spacing: Spacing,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.small)
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val title: String
        when (model.type) {
            TransactionType.DEPOSIT -> {
                DepositIcon(
                    modifier = Modifier.align(Alignment.Top)
                )
                title = stringResource(
                    id = R.string.deposit,
                )
            }

            TransactionType.WITHDRAWAL -> {
                WithdrawalIcon(
                    modifier = Modifier.align(Alignment.Top)
                )
                title = stringResource(id = R.string.withdrawal)
            }
        }
        Spacer(
            Modifier.width(width = spacing.extraSmall)
        )
        Column {
            Text(
                text = title,
            )
            Text(
                text = model.date.formatDateToString(),
            )
        }
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = model.value,
            style = MaterialTheme.typography.bodyLarge,
        )
        ChevronRightIcon()
    }
}

data class TransactionModel(
    val date: Date,
    val value: String,
    val description: String,
    val type: TransactionType,
)

enum class TransactionType {
    WITHDRAWAL, DEPOSIT
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TransactionItemPreview(
    @PreviewParameter(TransactionItemDataProvider::class) model: TransactionModel,
) {
    StoriTheme {
        TransactionItem(model = model, spacing = Spacing(), onClick = {})
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TransactionItemPreviewNight(
    @PreviewParameter(TransactionItemDataProvider::class) model: TransactionModel,
) {
    StoriTheme {
        TransactionItem(model = model, spacing = Spacing(), onClick = {})
    }
}
