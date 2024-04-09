package com.danchez.stori.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danchez.stori.ui.auth.AuthenticationScreen
import com.danchez.stori.ui.home.HomeScreen
import com.danchez.stori.ui.home.make_transaction.MakeTransactionScreen
import com.danchez.stori.ui.signup.SignUpScreen
import com.danchez.stori.ui.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplashScreen.route
    ) {
        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(AppScreens.AuthenticationScreen.route) {
            AuthenticationScreen(navController = navController)
        }
        composable(AppScreens.SignUpScreen.route) {
            SignUpScreen(navController = navController)
        }
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(AppScreens.MakeTransactionScreen.route) {
            MakeTransactionScreen(navController = navController)
        }
    }
}
