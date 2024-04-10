package com.danchez.stori.ui.home.make_transaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.danchez.stori.R
import com.danchez.stori.ui.common.CommonTextField
import com.danchez.stori.ui.theme.Spacing

@Composable
fun WithdrawalContent(
    spacing: Spacing,
) {
    Column {
        Text(
            text = stringResource(id = R.string.enter_value_to_withdraw),
            style = MaterialTheme.typography.bodyMedium,
        )
        CommonTextField(
            value = "",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
        ) {

        }
        Spacer(modifier = Modifier.height(spacing.medium))
        Text(
            text = stringResource(id = R.string.enter_destination_account_to_transfer),
            style = MaterialTheme.typography.bodyMedium
        )
        CommonTextField(
            value = "",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        ) {

        }
        Spacer(modifier = Modifier.height(spacing.medium))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyMedium
        )
        CommonTextField(
            value = "",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        ) {

        }
    }
}
