package com.danchez.stori.ui.create_transaction

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danchez.stori.R
import com.danchez.stori.domain.model.TransactionType
import com.danchez.stori.ui.common.LoadingDialog
import com.danchez.stori.ui.common.SimpleAlertDialog
import com.danchez.stori.ui.common.SimpleErrorAlertDialog
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing
import com.danchez.stori.utils.rememberImeState

@Composable
fun CreateTransactionScreen(
    navController: NavController,
    viewModel: CreateTransactionViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uIState.collectAsState()
    val localFocusManager = LocalFocusManager.current
    val clearFocus = { localFocusManager.clearFocus() }

    Scaffold { padding ->
        CreateTransactionContent(
            modifier = Modifier.padding(padding),
            navController = navController,
            value = viewModel.value,
            onValueChange = { viewModel.updateValue(it) },
            destinationAccount = viewModel.destinationAccount,
            onDestinationAccountChange = { viewModel.updateDestinationAccount(it) },
            description = viewModel.description,
            onDescriptionChange = { viewModel.updateDescription(it) },
            onCreateTransaction = {
                clearFocus()
                viewModel.createTransaction()
            },
            transactionTypeSelected = viewModel.transactionTypeSelected,
            onTransactionTypeChange = { viewModel.updateTransactionTypeSelected(it) },
            onConfirmationDialogs = { viewModel.hideDialogs() },
            clearFocus = clearFocus,
            uiState = uiState,
        )
    }
}

@Composable
private fun CreateTransactionContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    destinationAccount: String = "",
    onDestinationAccountChange: (String) -> Unit = {},
    description: String = "",
    onDescriptionChange: (String) -> Unit = {},
    onCreateTransaction: () -> Unit = {},
    transactionTypeSelected: TransactionType,
    onTransactionTypeChange: (TransactionType) -> Unit = {},
    onConfirmationDialogs: () -> Unit = {},
    clearFocus: () -> Unit = {},
    uiState: CreateTransactionUIState,
) {
    val spacing = MaterialTheme.spacing

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.value, tween(300))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(spacing.medium)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    clearFocus()
                })
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(spacing.small))
                .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp))
                .padding(spacing.medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = stringResource(id = R.string.account))
                Text(text = "1234 5678 9012")
            }
            Text(text = "$2.000.000,00")
        }
        Spacer(modifier = Modifier.height(spacing.large))
        TransactionTypeSelectorComposable(
            transactionTypeSelected = transactionTypeSelected,
            onOptionSelected = onTransactionTypeChange,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        when (transactionTypeSelected) {
            TransactionType.INCOME -> {
                IncomeContentComposable(
                    spacing = spacing,
                    value = value,
                    onValueChange = onValueChange,
                    description = description,
                    onDescriptionChange = onDescriptionChange,
                    uiState = uiState,
                )
            }

            TransactionType.WITHDRAWAL -> {
                WithdrawalContentComposable(
                    spacing = spacing,
                    value = value,
                    onValueChange = onValueChange,
                    destinationAccount = destinationAccount,
                    onDestinationAccountChange = onDestinationAccountChange,
                    description = description,
                    onDescriptionChange = onDescriptionChange,
                    uiState = uiState,
                )
            }
        }
        Spacer(modifier = Modifier.height(spacing.large))
        StoriButton(
            enabled = uiState.isButtonEnabled,
            onClick = onCreateTransaction,
            text = stringResource(id = R.string.make_transaction),
        )
        when (uiState.state) {
            CreateTransactionUIState.UIState.Initialized -> {}
            CreateTransactionUIState.UIState.Failure -> {
                SimpleErrorAlertDialog(
                    onConfirmation = onConfirmationDialogs,
                )
            }

            CreateTransactionUIState.UIState.Loading -> LoadingDialog()
            CreateTransactionUIState.UIState.Success -> TransactionCreatedDialog(
                navController = navController,
                onConfirmation = onConfirmationDialogs,
            )
        }
    }
}

@Composable
private fun TransactionCreatedDialog(
    navController: NavController,
    onConfirmation: () -> Unit = {},
) {
    SimpleAlertDialog(
        onConfirmation = {
            onConfirmation()
            navController.popBackStack()
        },
        onConfirmationLabel = stringResource(id = R.string.confirm),
        dialogText = stringResource(id = R.string.transaction_created),
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun CreateTransactionContentPreview() {
    StoriTheme {
        CreateTransactionContent(
            modifier = Modifier,
            navController = rememberNavController(),
            transactionTypeSelected = TransactionType.INCOME,
            uiState = CreateTransactionUIState(),
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateTransactionContentPreviewNight() {
    StoriTheme {
        CreateTransactionContent(
            modifier = Modifier,
            navController = rememberNavController(),
            transactionTypeSelected = TransactionType.INCOME,
            uiState = CreateTransactionUIState(),
        )
    }
}
