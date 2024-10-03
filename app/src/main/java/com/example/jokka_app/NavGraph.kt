package com.example.jokka_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import Features.Auth.SignInScreen
import Features.Auth.SignUpScreen
import Features.Auth.SplashScreen
import Features.Home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
        // Tambahkan rute lain di sini jika diperlukan
    }
}

// Define the screens in your app
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("sign_in")
    object Home : Screen("home")
    object SignUp : Screen("sign_up")
    // Tambahkan objek layar lainnya di sini jika diperlukan
}