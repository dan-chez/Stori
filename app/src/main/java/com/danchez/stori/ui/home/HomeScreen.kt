package com.danchez.stori.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.common.LoadingDialog
import com.danchez.stori.ui.common.SimpleErrorAlertDialog
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.signup.ProfilePicSection
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                viewModel.getHomeData()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(uiState.photoUrl)
                                .placeholder(R.drawable.profile_icon)
                                .error(R.drawable.profile_icon)
                                .build(),
                            contentDescription = "",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    shape = CircleShape,
                                ),
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        Text(text = uiState.displayName)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.onLogoutTap() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                        )
                    }
                }
            )
        }
    ) { padding ->
        HomeContent(
            modifier = Modifier.padding(padding),
            navController = navController,
            transactions = viewModel.transactions,
            account = viewModel.account,
            onConfirmationDialogs = { viewModel.onConfirmationDialogs() },
            onTransactionTap = { viewModel.onTransactionTap(it) },
            uiState = uiState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    modifier: Modifier,
    navController: NavController,
    transactions: List<TransactionUIModel> = emptyList(),
    account: AccountUIModel,
    onTransactionTap: (TransactionUIModel) -> Unit = {},
    onConfirmationDialogs: () -> Unit = {},
    uiState: HomeUIState,
) {
    val spacing = MaterialTheme.spacing

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
            model = account,
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
        if (transactions.isNotEmpty()) {
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
                items(transactions.size) {
                    val transaction = transactions[it]
                    TransactionItem(
                        model = transaction,
                        spacing = spacing,
                        onClick = {
                            onTransactionTap(transaction)
                        })
                    Divider(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                }
            }
        } else {
            Text(
                modifier = Modifier
                    .constrainAs(transactionsRef) {
                        top.linkTo(recentTranRef.bottom, spacing.small)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(buttonRef.top)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                text = stringResource(id = R.string.no_transactions),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            )
        }
        StoriButton(
            onClick = {
                navController.navigate(AppScreens.CreateTransactionScreen.route)
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
    when (uiState.state) {
        HomeUIState.UIState.Failure -> {
            SimpleErrorAlertDialog(
                onConfirmation = onConfirmationDialogs,
            )
        }

        HomeUIState.UIState.Initialized -> {}
        HomeUIState.UIState.Loading -> LoadingDialog()
        is HomeUIState.UIState.ShowDetailsSheet -> {
            val sheetState = rememberModalBottomSheetState()
            val scope = rememberCoroutineScope()
            TransactionDetail(
                sheetState = sheetState,
                transactionUIModel = uiState.state.transactionUIModel,
            ) {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onConfirmationDialogs()
                    }
                }
            }
        }

        HomeUIState.UIState.Success -> {}
        HomeUIState.UIState.Logout -> {
            navController.popBackStack()
            navController.navigate(route = AppScreens.AuthenticationScreen.route)
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeContentPreview() {
    StoriTheme {
        HomeContent(
            modifier = Modifier,
            navController = rememberNavController(),
            account = AccountUIModel.initial(),
            uiState = HomeUIState(),
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeContentPreviewNight() {
    StoriTheme {
        HomeContent(
            modifier = Modifier,
            navController = rememberNavController(),
            account = AccountUIModel.initial(),
            uiState = HomeUIState(),
        )
    }
}
