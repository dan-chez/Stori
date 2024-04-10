package com.danchez.stori.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.danchez.stori.R

@Composable
fun LoadingDialog() {
    Dialog(
        onDismissRequest = {},
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun SimpleErrorAlertDialog(
    onConfirmation: () -> Unit = {},
) {
    SimpleAlertDialog(
        onConfirmation = onConfirmation,
        onConfirmationLabel = stringResource(id = R.string.confirm),
        dialogTitle = stringResource(id = R.string.simple_error_dialog_title),
        dialogText = stringResource(id = R.string.simple_error_dialog_label),
    )
}

@Composable
fun SimpleAlertDialog(
    onConfirmation: () -> Unit,
    onConfirmationLabel: String,
    dialogTitle: String? = null,
    dialogText: String,
) {
    AlertDialog(
        title = dialogTitle?.let {
            { Text(text = it) }
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = onConfirmation,
        confirmButton = {
            TextButton(
                onClick = onConfirmation,
            ) {
                Text(onConfirmationLabel)
            }
        },
    )
}
