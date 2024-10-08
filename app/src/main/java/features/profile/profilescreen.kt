package features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jokka_app.R
import common.appbar.AppBar
import common.appbar.BottomBar
import common.button.Button
import common.button.OutlinedButton
import user.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    // Collect user data from ViewModel
    val userData = userViewModel.userData.collectAsState()

    // Handle fallback if data is not available
    val name = userData.value.name.ifEmpty { "Unknown User" }
    val phoneNumber = userData.value.phoneNumber.ifEmpty { "No Phone Number" }
    val email = userData.value.email.ifEmpty { "No Email" }

    // Get the current route from the NavController's back stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TopAppBar
        AppBar(
            title = "Profile",
            onNavigationIconClick = {
                // Handle back button action if needed
            }
        )

        // Content section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Profile Picture
            Image(
                painter = painterResource(id = R.drawable.logo_jokka_app),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            // Display Username
            Text(
                text = name, // Display dynamic name from ViewModel
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Display Phone Number
            Text(
                text = "Phone Number: $phoneNumber",  // Display phone number from ViewModel
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display Email
            Text(
                text = email, // Display email from ViewModel
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Edit Profile Button
            Button(
                text = "Edit Profile",
                onClick = {
                    navController.navigate("edit_profile")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Log Out Button
            OutlinedButton(
                text = "Log Out",
                onClick = {
                    navController.navigate("sign_in") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }

        // Bottom Navigation Bar - pass the current route dynamically
        BottomBar(
            currentScreen = currentRoute ?: "",  // Use the current route dynamically
            navController = navController,
            onItemSelected = { selectedScreen ->
                // Only navigate if not already on the selected screen
                if (selectedScreen != currentRoute) {
                    navController.navigate(selectedScreen)
                }
            }
        )
    }
}