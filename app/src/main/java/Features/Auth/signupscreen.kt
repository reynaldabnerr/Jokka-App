@file:Suppress("DEPRECATION")

package Features.Auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SignUpScreen(navController: NavController) {
    // State to hold the values for email, password, and confirm password
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }

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
                text = "Sign Up",
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
                textStyle = TextStyle(color = Color.Black)
            )

            // Password Input Field with Placeholder and Visual Transformation
            TextField(
                value = password,
                onValueChange = { newValue -> setPassword(newValue) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
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
                textStyle = TextStyle(color = Color.Black)
            )

            // Confirm Password Input Field
            TextField(
                value = confirmPassword,
                onValueChange = { newValue -> setConfirmPassword(newValue) },
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
                placeholder = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(), // Hides input with dots
                textStyle = TextStyle(color = Color.Black)
            )

            // Sign Up Button
            Button(
                onClick = {
                    // Handle Sign Up and Navigate to HomeScreen
                    navController.navigate("home") {
                        // Optional: Clear the back stack
                        popUpTo("sign_up") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(top = 16.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "Sign Up", color = Color.White)
            }

            // Footer with navigation to SignInScreen
            ClickableFooter(navController = navController)
        }
    }
}

@Composable
fun ClickableFooter(navController: NavController) {
    val annotatedText = buildAnnotatedString {
        append("Already have an account? ")

        // Annotate "Login" for click action
        pushStringAnnotation(tag = "Sign in", annotation = "Sign in")
        withStyle(style = SpanStyle(color = Color.Red, fontSize = 16.sp)) {
            append("Sign in")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        modifier = Modifier.padding(top = 16.dp),
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "Sign in", start = offset, end = offset)
                .firstOrNull()?.let {
                    // Navigate to SignInScreen
                    navController.navigate("sign_in")
                }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController()) // Provide a mock navController
}