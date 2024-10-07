package user

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserData(
    val name: String = "",
    val phoneNumber: String = "",
    val email: String = ""
)

class UserViewModel : ViewModel() {
    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData.asStateFlow()

    fun updateUserData(name: String, phoneNumber: String, email: String) {
        _userData.value = UserData(name, phoneNumber, email)
    }

    fun clearUserData() {
        _userData.value = UserData()
    }
}