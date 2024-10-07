package features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.jokka_app.R
import common.appbar.AppBar
import common.appbar.BottomBar
import common.button.Button
import common.button.OutlinedButton

@Composable
fun ProfileScreen(navController: NavController, name: String, email: String, phonenumber: String) {
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
                .weight(1f) // This ensures the content section takes available space
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
                    .clip(CircleShape)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )

            // Display Username
            Text(
                text = name, // Display dynamic name
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Display Phone Number
            Text(
                text = "Phone Number: $phonenumber",  // Display String phonenumber
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Display Email
            Text(
                text = email, // Display dynamic email
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                text = "Edit Profile",
                onClick = {
                    navController.navigate("edit_profile") {
                        popUpTo("sign_in") { inclusive = true }
                    }
                }
            )

            OutlinedButton(
                text = "Log Out",
                onClick = {
                    navController.navigate("sign_in")
                }
            )
        }

        // Bottom Navigation Bar
        BottomBar(
            currentScreen = "Profile",
            navController = navController,
            onItemSelected = { selectedScreen ->
                navController.navigate(selectedScreen)
            }
        )
    }
}