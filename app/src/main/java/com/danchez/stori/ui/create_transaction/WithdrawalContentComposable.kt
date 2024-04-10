package com.danchez.stori.ui.create_transaction

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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import com.danchez.stori.R
import com.danchez.stori.ui.common.CardNumberTextField
import com.danchez.stori.ui.common.CommonTextField
import com.danchez.stori.ui.theme.Spacing

@Composable
fun WithdrawalContentComposable(
    spacing: Spacing,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    destinationAccount: String = "",
    onDestinationAccountChange: (String) -> Unit = {},
    description: String = "",
    onDescriptionChange: (String) -> Unit = {},
    uiState: CreateTransactionUIState,
) {
    Column {
        Text(
            text = stringResource(id = R.string.enter_value_to_withdraw),
            style = MaterialTheme.typography.bodyMedium,
        )
        CommonTextField(
            value = value,
            isError = uiState.showValueError,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            onValueChange = onValueChange,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        Text(
            text = stringResource(id = R.string.enter_destination_account_to_transfer),
            style = MaterialTheme.typography.bodyMedium
        )
        CardNumberTextField(
            value = destinationAccount,
            isError = uiState.showDestinationAccountError,
            onValueChange = onDestinationAccountChange,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        Text(
            text = stringResource(id = R.string.description),
            style = MaterialTheme.typography.bodyMedium
        )
        CommonTextField(
            value = description,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Sentences,
            ),
            onValueChange = onDescriptionChange,
        )
    }
}
