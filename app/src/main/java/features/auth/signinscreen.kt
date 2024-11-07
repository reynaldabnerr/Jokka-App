@file:Suppress("DEPRECATION")

package features.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jokka_app.R
import common.button.Button
import common.button.OutlinedButton
import common.textfield.TextField
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun SignInScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // State management
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    // Configure Google Sign-In and GoogleSignInClient
    val googleSignInClient = GoogleSignIn.getClient(
        context as Activity,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    )

    // Google Sign-In launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        if (task.isSuccessful) {
            val account = task.result
            account?.let {
                firebaseAuthWithGoogle(it.idToken, auth, navController) // Authenticate with Firebase
            }
        } else {
            snackbarMessage = "Google sign-in failed."
            showSnackbar = true
        }
    }

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
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.signin_widget),
                contentDescription = "User sitting with laptop",
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(48.dp))

            Text("Hello Jokkers!", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.W400)
            Text("Please, Log In.", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholderText = "Your email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholderText = "Password",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email/Password Sign-In button
            Button(
                text = "Sign In",
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        snackbarMessage = "Please fill in email and password"
                        showSnackbar = true
                    } else {
                        firebaseAuthWithEmailPassword(email, password, auth, navController) {
                            snackbarMessage = it // Show error if any
                            showSnackbar = true
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(modifier = Modifier.weight(1f).align(Alignment.CenterVertically), color = Color.Gray)
                Text("or", modifier = Modifier.padding(horizontal = 16.dp), color = Color.Gray)
                Divider(modifier = Modifier.weight(1f).align(Alignment.CenterVertically), color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Sign-In button
            Button(
                text = "Sign in with Google",
                onClick = { launcher.launch(googleSignInClient.signInIntent) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                text = "Sign Up",
                onClick = { navController.navigate("sign_up") }
            )
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

// Firebase authentication with Google
private fun firebaseAuthWithGoogle(idToken: String?, auth: FirebaseAuth, navController: NavController) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("home") {
                    popUpTo("sign_in") { inclusive = true }
                }
            } else {
                // Log an error or display a snackbar on failure
            }
        }
}

// Firebase authentication with email/password
private fun firebaseAuthWithEmailPassword(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    onError: (String) -> Unit
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("home") {
                    popUpTo("sign_in") { inclusive = true }
                }
            } else {
                onError(task.exception?.message ?: "Email sign-in failed")
            }
        }
}