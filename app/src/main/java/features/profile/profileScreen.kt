package features.profile

import CustomButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.jokka_app.R
import common.appbar.AppBar
import common.appbar.BottomBar
import user.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val userData by userViewModel.userData.collectAsState()
    val name = userData.name.ifEmpty { "Unknown User" }
    val phoneNumber = userData.phoneNumber.ifEmpty { "No Phone Number" }
    val email = userData.email.ifEmpty { "No Email" }
    val profilePictureUrl = userData.profilePictureUrl.ifEmpty { "" }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // State management for popups
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppBar(
                title = "Profile"
            )
        },
        bottomBar = {
            BottomBar(
                currentScreen = currentRoute ?: "",
                navController = navController,
                onItemSelected = { selectedScreen ->
                    if (selectedScreen != currentRoute) {
                        navController.navigate(selectedScreen)
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFFDFD))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .shadow(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Load profile picture from URL
                        Image(
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = profilePictureUrl.ifEmpty { R.drawable.logo_jokka_app })
                                    .apply(block = fun ImageRequest.Builder.() {
                                        placeholder(R.drawable.logo_jokka_app)
                                        error(R.drawable.logo_jokka_app)
                                    }).build()
                            ),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .shadow(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        InfoRow(icon = Icons.Default.Phone, text = phoneNumber)
                        Spacer(modifier = Modifier.height(8.dp))
                        InfoRow(icon = Icons.Default.Email, text = email)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = "Edit Profile",
                    buttonColor = Color(0xFF4285F4), // Blue for edit profile
                    textColor = Color.White,
                    onClick = { navController.navigate("edit_profile") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = "Log Out",
                    buttonColor = Color(0xFFE53935), // Red for logout
                    textColor = Color.White,
                    onClick = { showLogoutDialog = true },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    text = "Delete Account",
                    buttonColor = Color(0xFF8E24AA), // Purple for delete account
                    textColor = Color.White,
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    // Logout confirmation dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Confirm Logout") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    userViewModel.logOut()
                    navController.navigate("sign_in") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                    showLogoutDialog = false
                }) {
                    Text("Logout")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete account confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Delete Account") },
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    userViewModel.deleteAccount(
                        onSuccess = {
                            navController.navigate("sign_in") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                            showDeleteDialog = false
                        },
                        onError = {
                            showDeleteDialog = false
                        }
                    )
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}