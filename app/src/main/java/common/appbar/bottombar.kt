package common.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomBar(
    currentScreen: String,
    onItemSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        val items = listOf(
            BottomBarItem("Home", Icons.Default.Home),
            BottomBarItem("Destination", Icons.Default.Place),
            BottomBarItem("Food", Icons.Default.Restaurant), // Replacing with Fastfood icon
            BottomBarItem("Profile", Icons.Default.Person)
        )

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                selected = currentScreen == item.name,
                onClick = { onItemSelected(item.name) },
                label = { Text(item.name) },
                alwaysShowLabel = true, // Always show label, even when not selected
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

data class BottomBarItem(val name: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)