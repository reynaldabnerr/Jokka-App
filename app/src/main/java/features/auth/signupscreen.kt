@file:Suppress("DEPRECATION")

package features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
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
import com.example.jokka_app.Screen
import common.button.Button
import common.textfield.TextField
import user.UserViewModel

@Composable
fun SignUpScreen(navController: NavController, userViewModel: UserViewModel) {
    // State to hold the values for name, phone number, email, password, and confirm password
    val (name, setName) = remember { mutableStateOf("") }
    val (phonenumber, setPhonenumber) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (confirmPassword, setConfirmPassword) = remember { mutableStateOf("") }
    val (isTermsAccepted, setTermsAccepted) = remember { mutableStateOf(false) }

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

            // Name Input Field
            TextField(
                value = name,
                onValueChange = setName,
                placeholderText = "Name",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            // Phone Number Input Field
            TextField(
                value = phonenumber,
                onValueChange = setPhonenumber,
                placeholderText = "Phone Number",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            // Email Input Field
            TextField(
                value = email,
                onValueChange = setEmail,
                placeholderText = "Email",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            // Password Input Field
            TextField(
                value = password,
                onValueChange = setPassword,
                placeholderText = "Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            // Confirm Password Input Field
            TextField(
                value = confirmPassword,
                onValueChange = setConfirmPassword,
                placeholderText = "Confirm Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { setTermsAccepted(it) }
                )
                Text(
                    text = "I agree to the Terms and Conditions"
                )
            }
            // Sign Up Button
            Button(
                text = "Sign up",
                onClick = {
                    if (isTermsAccepted) {
                        // Update the UserViewModel with the entered data
                        userViewModel.updateUserData(name, phonenumber, email)
                        // Navigate to the profile screen
                        navController.navigate(Screen.Profile.route)
                    }
                },
                modifier = Modifier.alpha(if (isTermsAccepted) 1f else 0.5f)
            )

            // Call the ClickableFooter here to display it
            ClickableFooter(navController = navController)
        }
    }
}

// Move ClickableFooter outside the Column block to make it reusable
@Composable
fun ClickableFooter(navController: NavController) {
    val annotatedText = buildAnnotatedString {
        append("Already have an account? ")

        // Annotate "Sign in" for click action
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
                    navController.navigate("sign_in")
                }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    // Provide a mock or empty ViewModel for the preview
    val mockUserViewModel = UserViewModel()
    SignUpScreen(navController = rememberNavController(), userViewModel = mockUserViewModel)
}