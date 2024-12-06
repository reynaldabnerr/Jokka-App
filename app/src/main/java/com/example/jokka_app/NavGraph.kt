package com.example.jokka_app



import FoodScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import data.MainViewModel
import features.auth.CompleteProfileScreen
import features.auth.SignInScreen
import features.auth.SignUpScreen
import features.auth.SplashScreen
import features.destination.DestinationScreen
import features.event.EventScreen
import features.home.HomeScreen
import features.profile.EditProfileScreen
import features.profile.ProfileScreen
import data.UserViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
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
            HomeScreen(navController = navController, userViewModel = userViewModel, mainViewModel = MainViewModel())
        }
        composable(Screen.Event.route) {
            EventScreen(navController = navController)
        }
        composable(Screen.Destination.route) {
            DestinationScreen(navController = navController)
        }
        composable(Screen.Food.route) {
            FoodScreen(navController = navController, mainViewModel = MainViewModel())
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController, userViewModel = userViewModel)
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController, userViewModel = userViewModel)
        }
        composable("complete_profile") {
            CompleteProfileScreen(navController = navController, userViewModel = userViewModel)
        }
    }
}


// Define the screens in your app
sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object Home : Screen("home")
    data object Event : Screen("event")
    data object Destination : Screen("destination")
    data object Food : Screen("food")
    data object Profile : Screen("profile")
    data object EditProfile : Screen("edit_profile")
}