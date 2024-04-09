package com.danchez.stori.navigation
/**
 * Sealed class representing different screens in the application.
 * Each screen is associated with a unique route identifier.
 */
sealed class AppScreens(val route: String) {
    data object SplashScreen : AppScreens("splash_screen")
    data object AuthenticationScreen : AppScreens("authentication_screen")
    data object SignUpScreen : AppScreens("sign_up_screen")
    data object HomeScreen : AppScreens("home_screen")
    data object MakeTransactionScreen : AppScreens("make_transaction_screen")
}
