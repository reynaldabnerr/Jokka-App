package common.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.jokka_app.Screen


@Composable
fun BottomBar(
    currentScreen: String,
    navController: NavController, // Add NavController parameter
    onItemSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        val items = listOf(
            BottomBarItem("Home", Icons.Default.Home, Screen.Home.route),
            BottomBarItem("Destination", Icons.Default.Place, "destination"),
            BottomBarItem("Food", Icons.Default.Menu, "food"),
            BottomBarItem("Profile", Icons.Default.Person, Screen.Profile.route) // Navigate to ProfileScreen
        )

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                selected = currentScreen == item.name,
                onClick = {
                    navController.navigate(item.route) // Navigate to the corresponding screen
                    onItemSelected(item.name) // Update the selected screen
                },
                label = { Text(item.name) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Red,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color.Red,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

data class BottomBarItem(val name: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val route: String)