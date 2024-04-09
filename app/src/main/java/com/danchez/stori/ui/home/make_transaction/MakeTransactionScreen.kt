package com.danchez.stori.ui.home.make_transaction

import android.content.res.Configuration
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danchez.stori.R
import com.danchez.stori.ui.common.CommonTextField
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.home.TransactionType
import com.danchez.stori.ui.theme.Spacing
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing

@Composable
fun MakeTransactionScreen(
    navController: NavController,
) {
    Scaffold { padding ->
        MakeTransactionContent(
            modifier = Modifier.padding(padding),
            navController = navController,
        )
    }
}

@Composable
private fun MakeTransactionContent(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val spacing = MaterialTheme.spacing
    val localFocusManager = LocalFocusManager.current

    val radioOptions = listOf(
        Pair(stringResource(id = R.string.withdrawal), TransactionType.WITHDRAWAL),
        Pair(stringResource(id = R.string.deposit), TransactionType.DEPOSIT),
    )
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.medium)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
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
        TransactionTypeSelector(
            selectedOption = selectedOption,
            onOptionSelected = onOptionSelected,
            radioOptions = radioOptions,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        if (selectedOption.second == TransactionType.WITHDRAWAL) {
            WithdrawalContent(
                spacing = spacing,
            )
        } else {
            DepositContent(
                spacing = spacing,
            )
        }
        Spacer(modifier = Modifier.height(spacing.large))
        StoriButton(
            onClick = {
                navController.popBackStack()
            },
            text = stringResource(id = R.string.make_transaction),
        )
    }
}

@Composable
private fun TransactionTypeSelector(
    selectedOption: Pair<String, TransactionType>,
    onOptionSelected: (Pair<String, TransactionType>) -> Unit,
    radioOptions: List<Pair<String, TransactionType>>,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        radioOptions.forEach { option ->
            val text = option.first
            Row(
                Modifier
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = {
                            onOptionSelected(option)
                        }
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (option == selectedOption),
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun MakeTransactionContentPreview() {
    StoriTheme {
        MakeTransactionContent(modifier = Modifier, navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MakeTransactionContentPreviewNight() {
    StoriTheme {
        MakeTransactionContent(modifier = Modifier, navController = rememberNavController())
    }
}
