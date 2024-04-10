package com.danchez.stori.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.danchez.stori.R
import com.danchez.stori.ui.home.preview.AccountInfoDataProvider
import com.danchez.stori.ui.theme.Spacing
import com.danchez.stori.ui.theme.StoriTheme

@Composable
fun AccountInfo(
    spacing: Spacing,
    modifier: Modifier = Modifier,
    model: AccountUIModel,
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = spacing.small,
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(spacing.medium)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.account),
            )
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = model.account,
            )
            Spacer(modifier = Modifier.height(spacing.medium))
            Divider(color = MaterialTheme.colorScheme.onBackground, thickness = 1.dp)
            Spacer(modifier = Modifier.height(spacing.medium))
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(id = R.string.total_balance),
            )
            Spacer(modifier = Modifier.height(spacing.small))
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = model.totalBalance,
            )
        }
    }
}

data class AccountUIModel(
    val account: String,
    val totalBalance: String,
) {
    companion object {
        fun initial(): AccountUIModel {
            return  AccountUIModel("", "0")
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AccountInfoPreview(
    @PreviewParameter(AccountInfoDataProvider::class) model: AccountUIModel,
) {
    StoriTheme {
        AccountInfo(spacing = Spacing(), model = model)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AccountInfoPreviewNight(
    @PreviewParameter(AccountInfoDataProvider::class) model: AccountUIModel,
) {
    StoriTheme {
        AccountInfo(spacing = Spacing(), model = model)
    }
}
