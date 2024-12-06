package features.destination

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokka_app.R
import common.cardScreen.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DestinationViewModel(application: Application) : AndroidViewModel(application) {
    private val destinationPreferencesDataStore = DestinationPreferencesDataStore(application)

    private val _destinations = MutableStateFlow<List<Destination>>(emptyList())
    val destinations: StateFlow<List<Destination>> = _destinations.asStateFlow()

    private val _likedDestinationIds = MutableStateFlow<Set<Int>>(emptySet())
    val likedDestinationIds: StateFlow<Set<Int>> = _likedDestinationIds.asStateFlow()

    init {
        loadDestinations()
        viewModelScope.launch {
            destinationPreferencesDataStore.likedDestinationIdsFlow.collectLatest { likedIds ->
                _likedDestinationIds.value = likedIds
            }
        }
    }

    private fun loadDestinations() {
        val destinationList = listOf(
            Destination(
                id = 1,
                imageResId = R.drawable.destination1,
                nameResId = R.string.destination1,
                categoryResId = R.string.category1,
                descriptionResId = R.string.destination_description1  // Tambahkan deskripsi
            ),
            Destination(
                id = 2,
                imageResId = R.drawable.destination2,
                nameResId = R.string.destination2,
                categoryResId = R.string.category2,
                descriptionResId = R.string.destination_description2  // Tambahkan deskripsi
            ),
            Destination(
                id = 3,
                imageResId = R.drawable.destination3,
                nameResId = R.string.destination3,
                categoryResId = R.string.category3,
                descriptionResId = R.string.destination_description3  // Tambahkan deskripsi
            ),
            Destination(
                id = 4,
                imageResId = R.drawable.destination4,
                nameResId = R.string.destination4,
                categoryResId = R.string.category4,
                descriptionResId = R.string.destination_description4  // Tambahkan deskripsi
            ),
            Destination(
                id = 5,
                imageResId = R.drawable.destination5,
                nameResId = R.string.destination5,
                categoryResId = R.string.category5,
                descriptionResId = R.string.destination_description5  // Tambahkan deskripsi
            )
        )
        _destinations.value = destinationList
    }

    fun toggleLike(destinationId: Int) {
        viewModelScope.launch {
            val currentLikedIds = _likedDestinationIds.value.toMutableSet()
            if (currentLikedIds.contains(destinationId)) {
                currentLikedIds.remove(destinationId)
            } else {
                currentLikedIds.add(destinationId)
            }
            _likedDestinationIds.value = currentLikedIds
            destinationPreferencesDataStore.updateLikedDestinationIds(currentLikedIds)
        }
    }

    fun isLiked(destinationId: Int): Boolean {
        return _likedDestinationIds.value.contains(destinationId)
    }
}