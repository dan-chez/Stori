package com.danchez.stori.ui.splash

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.danchez.stori.R
import com.danchez.stori.navigation.AppScreens
import com.danchez.stori.ui.theme.StoriTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(1000)
        navController.popBackStack()
        navController.navigate(AppScreens.AuthenticationScreen.route)
    }
    Scaffold { padding ->
        Splash(modifier = Modifier.padding(padding))
    }
}

@Composable
private fun Splash(
    modifier: Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
            modifier = Modifier.size(width = 150.dp, height = 70.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashPreview() {
    StoriTheme {
        Splash(modifier = Modifier)
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SplashPreviewNight() {
    StoriTheme {
        Splash(modifier = Modifier)
    }
}
