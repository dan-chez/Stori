package com.danchez.stori.ui.auth

import android.content.res.Configuration
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.common.EmailTextField
import com.danchez.stori.ui.common.LoadingDialog
import com.danchez.stori.ui.common.PasswordTextField
import com.danchez.stori.ui.common.SimpleErrorAlertDialog
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing

@Composable
fun AuthenticationScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel(),
) {
    val authUIState by viewModel.authState.collectAsState()
    val localFocusManager = LocalFocusManager.current
    val clearFocus = { localFocusManager.clearFocus() }
    Scaffold { padding ->
        AuthenticationContent(
            modifier = Modifier.padding(padding),
            navController = navController,
            email = viewModel.email,
            onEmailChanged = { viewModel.updateEmail(it) },
            password = viewModel.password,
            onPasswordChanged = { viewModel.updatePassword(it) },
            onLogin = {
                clearFocus()
                viewModel.login()
            },
            onConfirmationDialogs = { viewModel.hideDialogs() },
            clearFocus = clearFocus,
            authUIState = authUIState,
        )
    }
}

@Composable
fun AuthenticationContent(
    modifier: Modifier,
    navController: NavController,
    email: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    onLogin: () -> Unit = {},
    onConfirmationDialogs: () -> Unit = {},
    clearFocus: () -> Unit = {},
    authUIState: AuthenticationUIState,
) {
    val spacing = MaterialTheme.spacing
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(spacing.medium)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    clearFocus()
                })
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            modifier = Modifier.size(80.dp),
            // Stori icon
            model = "https://media.licdn.com/dms/image/D4E0BAQHuxJutLmsBFQ/company-logo_200_200/0/1700583469952?e=1720656000&v=beta&t=Dr_ltpO_Mi1IfFHEbMdBqIKK-DTlAhD4e2HM0TaQWxQ",
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.welcome),
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        EmailTextField(
            value = email,
            isError = authUIState.showEmailError,
            onValueChange = onEmailChanged,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        PasswordTextField(
            value = password,
            isError = authUIState.showPasswordError,
            onValueChange = onPasswordChanged,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        StoriButton(
            enabled = authUIState.isButtonEnabled,
            onClick = onLogin,
            text = stringResource(id = R.string.login_button),
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        TextButton(
            onClick = {
                navController.navigate(AppScreens.SignUpScreen.route)
            },
        ) {
            Text(
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                text = stringResource(id = R.string.sign_up),
            )

        }
        when (authUIState.state) {
            AuthenticationUIState.UIState.Initialized -> {}
            AuthenticationUIState.UIState.AuthenticationFailure -> {
                SimpleErrorAlertDialog(
                    onConfirmation = onConfirmationDialogs,
                )
            }

            AuthenticationUIState.UIState.AuthenticationSuccess -> {
                navController.navigate(AppScreens.HomeScreen.route)
            }

            AuthenticationUIState.UIState.Loading -> LoadingDialog()
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AuthenticationContentPreviewLight() {
    StoriTheme {
        AuthenticationContent(
            modifier = Modifier,
            navController = rememberNavController(),
            authUIState = AuthenticationUIState(),
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthenticationContentPreviewNight() {
    StoriTheme {
        AuthenticationContent(
            modifier = Modifier,
            navController = rememberNavController(),
            authUIState = AuthenticationUIState(),
        )
    }
}
