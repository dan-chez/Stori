package com.danchez.stori.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing
import kotlinx.coroutines.launch
import java.util.Date

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold { padding ->
        HomeContent(
            modifier = Modifier.padding(padding),
            navController = navController,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier,
    navController: NavController,
) {
    val spacing = MaterialTheme.spacing

    var showBottomSheet by remember { mutableStateOf(false) }

    // FIXME Remove mock data
    val accountModel = AccountModel(account = "1234 5678 9012", totalBalance = "\$2.000.000,00")
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(spacing.medium),
    ) {
        val (accountInfoRef, recentTranRef, transactionsRef, buttonRef) = createRefs()
        AccountInfo(
            spacing = spacing,
            modifier = Modifier
                .constrainAs(accountInfoRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .wrapContentSize(),
            model = accountModel,
        )
        Text(
            modifier = Modifier
                .constrainAs(recentTranRef) {
                    top.linkTo(accountInfoRef.bottom, spacing.large)
                    start.linkTo(parent.start)
                    width = Dimension.fillToConstraints
                }
                .wrapContentSize(),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(id = R.string.recent_transactions),
        )
        LazyColumn(
            modifier = Modifier
                .constrainAs(transactionsRef) {
                    top.linkTo(recentTranRef.bottom, spacing.small)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(buttonRef.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(8.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface,
                    shape = RoundedCornerShape(8.dp),
                )
        ) {
            items(2) {
                val transactionModel =
                    TransactionModel(
                        Date(),
                        "$500.000,00",
                        "",
                        if (it % 2 == 0) TransactionType.WITHDRAWAL else TransactionType.DEPOSIT,
                    )
                TransactionItem(
                    model = transactionModel,
                    spacing = spacing,
                    onClick = {
                        showBottomSheet = true
                    })
                Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
            }
        }
        StoriButton(
            onClick = {
                navController.navigate(AppScreens.MakeTransactionScreen.route)
            },
            modifier = Modifier
                .constrainAs(buttonRef) {
                    top.linkTo(transactionsRef.bottom, spacing.small)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxWidth()
                .height(56.dp),
            text = stringResource(id = R.string.make_transaction)
        )
    }
    if (showBottomSheet) {
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        TransactionDetail(
            sheetState = sheetState,
        ) {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeContentPreview() {
    StoriTheme {
        HomeContent(modifier = Modifier, navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreviewNight() {
    StoriTheme {
        HomeContent(modifier = Modifier, navController = rememberNavController())
    }
}
