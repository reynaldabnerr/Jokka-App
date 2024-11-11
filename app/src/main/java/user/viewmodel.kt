package user

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class UserData(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val profilePictureUrl: String = ""
)

class UserViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // StateFlow to hold user data
    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData

    // Register user and save their data in Firestore
    fun registerUser(
        name: String,
        phoneNumber: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid ?: ""
                    val userData = mapOf(
                        "name" to name,
                        "phone_number" to phoneNumber,
                        "email" to email,
                        "profile_picture_url" to ""  // Tambahkan URL foto profil jika ada
                    )

                    firestore.collection("users").document(userId).set(userData)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { e ->
                            onError(e.message ?: "Failed to save user data.")
                        }
                } else {
                    onError(task.exception?.message ?: "Failed to create account.")
                }
            }
    }

    // Fetch user data from Firestore
    private fun fetchUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name") ?: ""
                        val phoneNumber = document.getString("phone_number") ?: ""
                        val email = document.getString("email") ?: ""
                        val profilePictureUrl = document.getString("profile_picture_url") ?: ""
                        _userData.value = UserData(name, phoneNumber, email, profilePictureUrl)
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle error (e.g., log it or show an error message)
                }
        }
    }

    // Update user data in Firestore
    fun updateUserData(
        name: String,
        phoneNumber: String,
        profilePictureUrl: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val updatedData = mapOf(
                "name" to name,
                "phone_number" to phoneNumber,
                "profile_picture_url" to profilePictureUrl
            )

            firestore.collection("users").document(userId).update(updatedData)
                .addOnSuccessListener {
                    _userData.value = UserData(name, phoneNumber, currentUser.email ?: "", profilePictureUrl)
                    onSuccess()
                }
                .addOnFailureListener { e ->
                    onError(e.message ?: "Failed to update user data.")
                }
        } else {
            onError("User is not logged in.")
        }
    }

    // Log out the user and clear user data
    fun logOut() {
        auth.signOut()
        _userData.value = UserData()  // Clear local user data
    }

    // Call fetchUserData when initializing the ViewModel
    init {
        fetchUserData()
    }
}
