@file:Suppress("DEPRECATION")

package features.auth

import CustomButton
import user.UserViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jokka_app.R
import com.example.jokka_app.Screen
import common.textfield.TextField


@Composable
fun SignUpScreen(navController: NavController, userViewModel: UserViewModel) {
    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isTermsAccepted by remember { mutableStateOf(false) }

    // Snackbar state
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

    // State to show success message
    var showSuccessSnackbar by remember { mutableStateOf(false) }

    val gradientColors = listOf(
        Color(0xFFFCE4EC),  // Light pink
        Color(0xFFF3E5F5),  // Light purple
        Color(0xFFE8EAF6)   // Light indigo
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.signup_widget),
                contentDescription = "User illustration",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Create Account",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Please fill in the form to continue",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                placeholderText = "Name",
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = phonenumber,
                onValueChange = { phonenumber = it },
                placeholderText = "Phone Number",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholderText = "Email",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholderText = "Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholderText = "Confirm Password",
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it }
                )
                Text(
                    text = "I agree to the Terms and Conditions",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Sign Up",
                buttonColor = if (isTermsAccepted) Color.Red else Color.Gray,
                textColor = Color.White,
                onClick = {
                    when {
                        !isTermsAccepted -> {
                            snackbarMessage = "Please accept the Terms and Conditions"
                            showSnackbar = true
                        }
                        name.isEmpty() || phonenumber.isEmpty() || email.isEmpty() ||
                                password.isEmpty() || confirmPassword.isEmpty() -> {
                            snackbarMessage = "Please fill in all fields"
                            showSnackbar = true
                        }
                        password != confirmPassword -> {
                            snackbarMessage = "Passwords do not match"
                            showSnackbar = true
                        }
                        else -> {
                            userViewModel.registerUser(
                                name = name,
                                phoneNumber = phonenumber,
                                email = email,
                                password = password,
                                onSuccess = {
                                    snackbarMessage = "Account successfully created"
                                    showSnackbar = true
                                    showSuccessSnackbar = true // Tampilkan pesan sukses
                                },
                                onError = { error ->
                                    snackbarMessage = error
                                    showSnackbar = true
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.alpha(if (isTermsAccepted) 1f else 0.5f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ClickableFooter(navController = navController)
        }

        // Snackbar
        if (showSnackbar) {
            LaunchedEffect(snackbarHostState, showSuccessSnackbar) {
                snackbarHostState.showSnackbar(
                    message = snackbarMessage,
                    duration = SnackbarDuration.Short
                )
                showSnackbar = false

                // Navigate to Home if signup is successful
                if (showSuccessSnackbar) {
                    showSuccessSnackbar = false
                    navController.navigate(Screen.Home.route)
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) { data ->
            Snackbar(
                modifier = Modifier.padding(horizontal = 16.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ) {
                Text(data.visuals.message)
            }
        }
    }
}

@Composable
fun ClickableFooter(navController: NavController) {
    val annotatedText = buildAnnotatedString {
        append("Already have an account? ")
        pushStringAnnotation(tag = "Sign in", annotation = "Sign in")
        withStyle(style = SpanStyle(color = Color.Red, fontSize = 16.sp, textDecoration = TextDecoration.Underline)) {
            append("Sign in")
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "Sign in", start = offset, end = offset)
                .firstOrNull()?.let {
                    navController.navigate("sign_in")
                }
        },
        modifier = Modifier.padding(top = 16.dp)
    )
}