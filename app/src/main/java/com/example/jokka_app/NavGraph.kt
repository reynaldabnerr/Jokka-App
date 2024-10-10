package com.example.jokka_app

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import features.auth.SignInScreen
import features.auth.SignUpScreen
import features.auth.SplashScreen
import features.destination.DestinationScreen
//import features.event.EventScreen
import features.food.FoodScreen
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
            SignInScreen(navController = navController)
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(navController = navController, userViewModel = userViewModel)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, userViewModel = userViewModel)
        }
//        composable(Screen.Event.route) {
//            EventScreen(navController = navController)
//        }
        composable(Screen.Destination.route) {
            DestinationScreen(navController = navController)
        }
        composable(Screen.Food.route) {
            FoodScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, userViewModel = userViewModel)
        }
    }
}

// Define the screens in your app
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object Home : Screen("home")
//    data object Event : Screen("event")
    data object Destination : Screen("destination")
    data object Food : Screen("food")
    data object Profile : Screen("profile")
}