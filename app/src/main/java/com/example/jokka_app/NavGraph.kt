package com.example.jokka_app

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import features.auth.SignInScreen
import features.auth.SignUpScreen
import features.auth.SplashScreen
import features.home.HomeScreen
import features.profile.ProfileScreen
import user.UserViewModel

@Composable
fun NavGraph(navController: NavHostController, userViewModel: UserViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController, userViewModel = userViewModel)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController, userViewModel = userViewModel)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, userViewModel = userViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
    }
}

// Define the screens in your app
sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object SignIn : Screen("sign_in")
    object Home : Screen("home")
    object SignUp : Screen("sign_up")
    object Profile : Screen("profile")
}