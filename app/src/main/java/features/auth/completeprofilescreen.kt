package features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import common.textfield.TextField
import user.UserViewModel

@Composable
fun CompleteProfileScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    // State management
    var name by remember { mutableStateOf(userViewModel.userData.value.name) }
    var phoneNumber by remember { mutableStateOf(userViewModel.userData.value.phoneNumber) }
    var profilePictureUrl by remember { mutableStateOf(userViewModel.userData.value.profilePictureUrl) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = listOf(Color(0xFFFCE4EC), Color(0xFFF3E5F5), Color(0xFFE8EAF6)))
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Complete Your Profile",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                placeholderText = "Your name",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                placeholderText = "Phone number",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = profilePictureUrl,
                onValueChange = { profilePictureUrl = it },
                placeholderText = "Profile picture URL",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri, imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isEmpty() || phoneNumber.isEmpty() || profilePictureUrl.isEmpty()) {
                        snackbarMessage = "All fields are required."
                        showSnackbar = true
                    } else {
                        userViewModel.updateUserData(
                            name = name,
                            phoneNumber = phoneNumber,
                            profilePictureUrl = profilePictureUrl,
                            onSuccess = {
                                snackbarMessage = "Profile updated successfully"
                                showSnackbar = true
                                navController.navigate("home") {
                                    popUpTo("complete_profile") { inclusive = true }
                                }
                            },
                            onError = { error ->
                                snackbarMessage = error
                                showSnackbar = true
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Profile")
            }
        }

        if (showSnackbar) {
            LaunchedEffect(snackbarHostState) {
                snackbarHostState.showSnackbar(
                    message = snackbarMessage,
                    duration = SnackbarDuration.Short
                )
                showSnackbar = false
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                modifier = Modifier.padding(horizontal = 16.dp),
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
            ) { Text(data.visuals.message) }
        }
    }
}