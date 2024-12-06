@file:Suppress("DEPRECATION")

package features.auth

import CustomButton
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
import com.example.jokka_app.R.drawable.google_icon
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import common.textfield.TextField


@Composable
fun SignInScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // State management
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        if (task.isSuccessful) {
            val account = task.result
            account?.let {
                firebaseAuthWithGoogle(it.idToken, auth, navController) { message ->
                    snackbarMessage = message // Update Snackbar message
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFCE4EC), Color(0xFFF3E5F5), Color(0xFFE8EAF6))
                        )
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

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(
                        onClick = {
                            if (email.isNotEmpty()) {
                                sendPasswordResetEmail(auth, email) { message ->
                                    snackbarMessage = message
                                }
                            } else {
                                snackbarMessage = "Please enter your email to reset password."
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomButton(
                        text = "Sign In",
                        buttonColor = Color.Red,
                        textColor = Color.White,
                        onClick = {
                            if (email.isEmpty() || password.isEmpty()) {
                                snackbarMessage = "Please fill in email and password"
                            } else {
                                firebaseAuthWithEmailPassword(email, password, auth, navController) { message ->
                                    snackbarMessage = message
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                        Text("or", modifier = Modifier.padding(horizontal = 16.dp), color = Color.Gray)
                        Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    CustomButton(
                        text = "Sign in with Google",
                        iconResId = google_icon,
                        buttonColor = Color.White,
                        textColor = Color.Black,
                        onClick = { launcher.launch(googleSignInClient.signInIntent) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    CustomButton(
                        text = "Sign Up",
                        buttonColor = Color.Gray,
                        textColor = Color.White,
                        onClick = { navController.navigate("sign_up") }
                    )
                }

                // Display Snackbar if there's a message
                LaunchedEffect(snackbarMessage) {
                    if (snackbarMessage.isNotEmpty()) {
                        snackbarHostState.showSnackbar(snackbarMessage)
                        snackbarMessage = "" // Reset message after showing
                    }
                }
            }
        }
    )
}
// Firebase function to send password reset email
private fun sendPasswordResetEmail(auth: FirebaseAuth, email: String, callback: (String) -> Unit) {
    auth.sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback("Reset password email sent to $email")
            } else {
                callback(task.exception?.message ?: "Failed to send reset password email")
            }
        }
}

// Firebase authentication with Google
private fun firebaseAuthWithGoogle(
    idToken: String?,
    auth: FirebaseAuth,
    navController: NavController,
    onResult: (String) -> Unit // Tambahkan callback untuk pesan sukses/gagal
) {
    val credential = GoogleAuthProvider.getCredential(idToken, null)
    auth.signInWithCredential(credential)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: ""
                val email = auth.currentUser?.email ?: ""

                val database = FirebaseFirestore.getInstance()
                val userDoc = database.collection("users").document(userId)

                userDoc.get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name").orEmpty()
                        val phoneNumber = document.getString("phone_number").orEmpty()
                        val profilePictureUrl = document.getString("profile_picture_url").orEmpty()

                        if (name.isNotEmpty() && phoneNumber.isNotEmpty() && profilePictureUrl.isNotEmpty()) {
                            navController.navigate("home") {
                                popUpTo("sign_in") { inclusive = true }
                            }
                            onResult("Sign in successful!")
                        } else {
                            navController.navigate("complete_profile") {
                                popUpTo("sign_in") { inclusive = true }
                            }
                            onResult("Sign in successful! Please complete your profile.")
                        }
                    } else {
                        val defaultData = mapOf(
                            "name" to "",
                            "phone_number" to "",
                            "profile_picture_url" to "",
                            "email" to email,
                            "role" to "user"
                        )
                        userDoc.set(defaultData).addOnSuccessListener {
                            navController.navigate("complete_profile") {
                                popUpTo("sign_in") { inclusive = true }
                            }
                            onResult("Sign in successful! Please complete your profile.")
                        }
                    }
                }.addOnFailureListener {
                    onResult("Failed to fetch user data: ${it.message}")
                }
            } else {
                onResult("Google Sign-In failed: ${task.exception?.message}")
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