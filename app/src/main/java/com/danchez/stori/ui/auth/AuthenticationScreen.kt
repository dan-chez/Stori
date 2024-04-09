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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.common.EmailTextField
import com.danchez.stori.ui.common.PasswordTextField
import com.danchez.stori.ui.common.StoriButton
import com.danchez.stori.ui.theme.StoriTheme
import com.danchez.stori.ui.theme.spacing

@Composable
fun AuthenticationScreen(
    navController: NavController,
) {
    Scaffold { padding ->
        AuthenticationContent(
            modifier = Modifier.padding(padding),
            navController = navController,
        )
    }
}

@Composable
fun AuthenticationContent(
    modifier: Modifier,
    navController: NavController,
) {
    val spacing = MaterialTheme.spacing
    val localFocusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(spacing.medium)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
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
            onValueChange = {
                println(it)
            }
        )
        Spacer(modifier = Modifier.height(spacing.small))
        PasswordTextField(
            onValueChange = {
                println(it)
            }
        )
        Spacer(modifier = Modifier.height(spacing.medium))
        StoriButton(
            onClick = {
                navController.popBackStack()
                navController.navigate(AppScreens.HomeScreen.route)
            },
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
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AuthenticationContentPreviewLight() {
    StoriTheme {
        AuthenticationContent(modifier = Modifier, navController = rememberNavController())
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AuthenticationContentPreviewNight() {
    StoriTheme {
        AuthenticationContent(modifier = Modifier, navController = rememberNavController())
    }
}
