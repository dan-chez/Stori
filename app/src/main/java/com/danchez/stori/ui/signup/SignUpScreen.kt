package com.danchez.stori.ui.signup

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.common.CommonTextField
import com.danchez.stori.ui.common.EmailTextField
import com.danchez.stori.ui.common.LoadingDialog
import com.danchez.stori.ui.common.PasswordTextField
import com.danchez.stori.ui.common.SimpleAlertDialog
import com.danchez.stori.ui.common.SimpleErrorAlertDialog
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing
import com.danchez.stori.utils.extensions.bitmapToUri
import com.danchez.stori.utils.rememberImeState

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
) {
    val signUpUIState by viewModel.signUpState.collectAsState()
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            newImage?.let { bitmap ->
                bitmap.bitmapToUri(context)?.let {
                    viewModel.updateUserPhoto(it)
                }
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch()
        }
    }

    Scaffold { padding ->
        SignUpContent(
            modifier = Modifier.padding(padding),
            navController = navController,
            name = viewModel.name,
            onNameChanged = { viewModel.updateName(it) },
            surname = viewModel.surname,
            onSurnameChanged = { viewModel.updateSurname(it) },
            email = viewModel.email,
            onEmailChanged = { viewModel.updateEmail(it) },
            password = viewModel.password,
            onPasswordChanged = { viewModel.updatePassword(it) },
            confirmPassword = viewModel.confirmPassword,
            onConfirmPasswordChanged = { viewModel.updateConfirmPassword(it) },
            onConfirmationDialogs = { viewModel.hideDialogs() },
            onSignUp = { viewModel.signUp() },
            onChangePicture = {
                viewModel.onChangePhoto(
                    context = context,
                    cameraLauncher = cameraLauncher,
                    permissionLauncher = permissionLauncher,
                )
            },
            imageUri = viewModel.userPhoto,
            signUpUIState = signUpUIState,
        )
    }
}

@Composable
fun SignUpContent(
    modifier: Modifier,
    navController: NavController,
    name: String = "",
    onNameChanged: (String) -> Unit = {},
    surname: String = "",
    onSurnameChanged: (String) -> Unit = {},
    email: String = "",
    onEmailChanged: (String) -> Unit = {},
    password: String = "",
    onPasswordChanged: (String) -> Unit = {},
    confirmPassword: String = "",
    onConfirmPasswordChanged: (String) -> Unit = {},
    onSignUp: () -> Unit = {},
    onConfirmationDialogs: () -> Unit = {},
    onChangePicture: () -> Unit = {},
    imageUri: Uri? = null,
    signUpUIState: SignUpUIState,
) {
    val spacing = MaterialTheme.spacing
    val localFocusManager = LocalFocusManager.current

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
                    localFocusManager.clearFocus()
                })
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(id = R.string.sign_up_title),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(spacing.large))
        ProfilePicSection(
            imageUri = imageUri,
            onChangePicture = onChangePicture,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        CommonTextField(
            value = name,
            isError = signUpUIState.showNameError,
            label = stringResource(id = R.string.user_name),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            ),
            onValueChange = onNameChanged,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        CommonTextField(
            value = surname,
            isError = signUpUIState.showSurnameError,
            label = stringResource(id = R.string.user_surname),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            ),
            onValueChange = onSurnameChanged,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        EmailTextField(
            value = email,
            isError = signUpUIState.showEmailError,
            onValueChange = onEmailChanged,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        PasswordTextField(
            imeAction = ImeAction.Next,
            value = password,
            isError = signUpUIState.showPasswordError,
            onValueChange = onPasswordChanged,
        )
        Spacer(modifier = Modifier.height(spacing.small))
        PasswordTextField(
            value = confirmPassword,
            supportingText = stringResource(id = R.string.passwords_not_match),
            isError = signUpUIState.showConfirmPasswordError,
            label = stringResource(id = R.string.confirm_password),
            onValueChange = onConfirmPasswordChanged,
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        StoriButton(
            enabled = signUpUIState.isButtonEnabled,
            onClick = onSignUp,
            text = stringResource(id = R.string.sign_up_button),
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        TextButton(
            onClick = {
                navController.popBackStack()
            },
        ) {
            Text(
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W600,
                text = stringResource(id = R.string.sign_in),
            )
        }
        when (signUpUIState.state) {
            SignUpUIState.UIState.Initialized -> {}
            SignUpUIState.UIState.Loading -> LoadingDialog()
            SignUpUIState.UIState.SignUpFailure -> {
                SimpleErrorAlertDialog(
                    onConfirmation = onConfirmationDialogs,
                )
            }

            SignUpUIState.UIState.SignUpSuccess -> SignUpSuccessDialog(
                navController = navController,
                onConfirmation = onConfirmationDialogs,
            )
        }
    }
}

@Composable
private fun SignUpSuccessDialog(
    navController: NavController,
    onConfirmation: () -> Unit = {},
) {
    SimpleAlertDialog(
        onConfirmation = {
            onConfirmation()
            navController.navigate(route = AppScreens.HomeScreen.route) {
                popUpTo(AppScreens.AuthenticationScreen.route) {
                    inclusive = true
                }
            }
        },
        onConfirmationLabel = stringResource(id = R.string.confirm),
        dialogText = stringResource(id = R.string.sign_up_success_text),
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignUpContentPreviewLight() {
    StoriTheme {
        SignUpContent(
            modifier = Modifier,
            navController = rememberNavController(),
            signUpUIState = SignUpUIState(),
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpContentPreviewNight() {
    StoriTheme {
        SignUpContent(
            modifier = Modifier,
            navController = rememberNavController(),
            signUpUIState = SignUpUIState(),
        )
    }
}
