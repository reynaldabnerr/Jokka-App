package features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import common.button.Button
import common.button.OutlinedButton
import common.textfield.TextField
import user.UserViewModel

@Composable
fun SignInScreen(navController: NavController, userViewModel: UserViewModel) {
    // Use remember to hold state of email and password
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                text = "Sign In",
                color = Color.Black,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Email Input Field with Placeholder
            TextField(
                value = email,
                onValueChange = setEmail,
                placeholderText = "Email",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            // Password Input Field with Placeholder and Visual Transformation
            TextField(
                value = password,
                onValueChange = setPassword,
                placeholderText = "Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation() // Hides input with dots
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Use the custom Button from common.button
            Button(
                text = "Sign In",
                onClick = {
                    // Navigate to HomeScreen
                    navController.navigate("home") {
                        // Optional: Clear the back stack
                        popUpTo("sign_in") { inclusive = true }
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Use the custom Outlined Button from common.button
            OutlinedButton(
                text = "Register",
                onClick = {
                    // Navigate to SignUpScreen
                    navController.navigate("sign_up")
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    // Provide a mock or empty ViewModel for the preview
    val mockUserViewModel = UserViewModel()
    SignInScreen(navController = rememberNavController(), userViewModel = mockUserViewModel)
}