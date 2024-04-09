package com.danchez.stori.ui.signup

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.common.CommonTextField
import com.danchez.stori.ui.common.EmailTextField
import com.danchez.stori.ui.common.PasswordTextField
import com.danchez.stori.ui.common.SimpleAlertDialog
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing

@Composable
fun SignUpScreen(
    navController: NavController,
) {
    Scaffold { padding ->
        SignUpContent(
            modifier = Modifier.padding(padding),
            navController = navController,
        )
    }
}

@Composable
fun SignUpContent(
    modifier: Modifier,
    navController: NavController,
) {
    val spacing = MaterialTheme.spacing
    val localFocusManager = LocalFocusManager.current

    var showSignUpSuccess by remember { mutableStateOf(false) }

    val onSignUp = { showSignUpSuccess = !showSignUpSuccess }

    Column(
        modifier = modifier
            .fillMaxSize()
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
        ProfilePic()
        Spacer(modifier = Modifier.height(spacing.medium))
        CommonTextField(
            label = stringResource(id = R.string.user_name),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            )
        ) {

        }
        Spacer(modifier = Modifier.height(spacing.small))
        CommonTextField(
            label = stringResource(id = R.string.user_surname),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next,
            )
        ) {

        }
        Spacer(modifier = Modifier.height(spacing.small))
        EmailTextField(
            onValueChange = {

            }
        )
        Spacer(modifier = Modifier.height(spacing.small))
        PasswordTextField(
            imeAction = ImeAction.Next,
            onValueChange = {

            }
        )
        Spacer(modifier = Modifier.height(spacing.small))
        PasswordTextField(
            label = stringResource(id = R.string.confirm_password),
            onValueChange = {

            }
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        StoriButton(
            onClick = {
                onSignUp()
            },
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
        SignUpSuccessDialog(
            navController = navController,
            onSignUp,
            showSignUpSuccess
        )
    }
}

@Composable
private fun SignUpSuccessDialog(
    navController: NavController,
    onSignUp: () -> Unit,
    showSignUpSuccess: Boolean,
) {
    SimpleAlertDialog(
        onDismissRequest = { onSignUp() },
        onConfirmation = {
            onSignUp()
            navController.navigate(route = AppScreens.HomeScreen.route) {
                popUpTo(AppScreens.AuthenticationScreen.route) {
                    inclusive = true
                }
            }
        },
        onConfirmationLabel = "Confirm",
        dialogTitle = "",
        dialogText = "",
        showDialog = showSignUpSuccess,
    )
}

@Composable
private fun ProfilePic() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground,
                    shape = CircleShape,
                ),
            painter = painterResource(id = R.drawable.profile_icon),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        TextButton(
            onClick = {

            }) {
            Text(
                text = stringResource(id = R.string.change_photo),
                style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignUpContentPreviewLight() {
    StoriTheme {
        SignUpContent(modifier = Modifier, navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpContentPreviewNight() {
    StoriTheme {
        SignUpContent(modifier = Modifier, navController = rememberNavController())
    }
}
