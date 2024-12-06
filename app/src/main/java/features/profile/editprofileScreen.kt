package features.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import common.appbar.AppBar
import data.UserViewModel

@Composable
fun EditProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val userData by userViewModel.userData.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()

    var name by remember { mutableStateOf(TextFieldValue(userData.name)) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue(userData.phoneNumber)) }
    var profilePictureUrl by remember { mutableStateOf(TextFieldValue(userData.profilePictureUrl)) }

    Scaffold(
        topBar = {
            AppBar(
                title = "Edit Profile",
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Edit Profile", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    ProfileInputField(
                        label = "Name",
                        icon = Icons.Default.Person,
                        textState = name,
                        onTextChange = { name = it }
                    )

                    ProfileInputField(
                        label = "Phone Number",
                        icon = Icons.Default.Phone,
                        textState = phoneNumber,
                        onTextChange = { phoneNumber = it }
                    )

                    ProfileInputField(
                        label = "Profile Picture URL",
                        icon = Icons.Default.Photo,
                        textState = profilePictureUrl,
                        onTextChange = { profilePictureUrl = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            userViewModel.updateUserData(
                                name = name.text,
                                phoneNumber = phoneNumber.text,
                                profilePictureUrl = profilePictureUrl.text,
                                onSuccess = {
                                    navController.popBackStack()
                                },
                                onError = { errorMessage ->
                                    println("Error updating profile: $errorMessage")
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text("Save", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileInputField(
    label: String,
    icon: ImageVector,
    textState: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = label, style = MaterialTheme.typography.labelSmall)
                BasicTextField(
                    value = textState,
                    onValueChange = onTextChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}
