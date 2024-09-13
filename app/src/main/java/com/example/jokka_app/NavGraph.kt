package com.example.jokka_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jokka_app.Auth.SplashScreen
import com.example.jokka_app.ui.home.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
        // Tambahkan rute lain di sini
    }
}

// Define the screens in your app
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    // Tambahkan objek layar lainnya di sini
}
