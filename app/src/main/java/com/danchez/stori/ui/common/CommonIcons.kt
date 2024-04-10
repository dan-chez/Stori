package com.danchez.stori.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.danchez.stori.ui.theme.deposit_color
import com.danchez.stori.ui.theme.withdrawal_color

@Composable
fun ChevronRightIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        Icons.Filled.ChevronRight,
        contentDescription = null,
        modifier = modifier,
    )
}

@Composable
fun DepositIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = Icons.Filled.ArrowDownward,
        contentDescription = null,
        modifier = modifier
            .rotate(-30f),
        tint = deposit_color,
    )
}

@Composable
fun WithdrawalIcon(
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = Icons.Filled.ArrowDownward,
        contentDescription = null,
        modifier = modifier
            .rotate(130f),
        tint = withdrawal_color,
    )
}

@Composable
fun TextFieldErrorIcon() {
    Icon(
        imageVector = Icons.Filled.Error,
        "error",
        tint = MaterialTheme.colorScheme.error,
    )
}
