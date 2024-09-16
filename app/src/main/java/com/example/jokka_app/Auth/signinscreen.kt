package com.example.jokka_app.Auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignInScreen(navController: NavController) {
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
                onValueChange = { newValue -> setEmail(newValue) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp)),
                placeholder = { Text("Email") },
                textStyle = TextStyle(color = Color.Black) // Ensure the text color is set
            )

            // Password Input Field with Placeholder and Visual Transformation
            TextField(
                value = password,
                onValueChange = { newValue -> setPassword(newValue) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp)),
                placeholder = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(), // Hides input with dots
                textStyle = TextStyle(color = Color.Black) // Ensure the text color is set
            )

            // Sign In Button
            Button(
                onClick = {
                    // Navigate to HomeScreen
                    navController.navigate("home") {
                        // Optional: Clear the back stack
                        popUpTo("sign_in") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 16.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Sign In", color = Color.White)
            }

            // Register Button (Optional)
            OutlinedButton(
                onClick = { /* TODO: Handle register */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 16.dp)
                    .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Register", color = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController()) // Provide a mock navController
}