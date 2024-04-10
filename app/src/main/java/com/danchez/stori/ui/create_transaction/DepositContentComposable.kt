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
import com.danchez.stori.ui.common.CommonTextField
import com.danchez.stori.ui.theme.Spacing

@Composable
fun DepositContentComposable(
    spacing: Spacing,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    description: String = "",
    onDescriptionChange: (String) -> Unit = {},
    uiState: CreateTransactionUIState,
) {
    Column {
        Text(
            text = stringResource(id = R.string.enter_value_to_deposit),
            style = MaterialTheme.typography.bodyMedium,
        )
        CommonTextField(
            value = value,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            onValueChange = onValueChange,
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
