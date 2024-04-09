package com.danchez.stori

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.danchez.stori.navigation.AppNavigation
import com.danchez.stori.ui.theme.StoriTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StoriTheme {
                AppNavigation()
            }
        }
    }
}
