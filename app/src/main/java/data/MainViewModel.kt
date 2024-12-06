package data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // State for Foods
    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods

    // State for Events
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    // State for Destinations
    private val _destinations = MutableStateFlow<List<Destination>>(emptyList())
    val destinations: StateFlow<List<Destination>> = _destinations

    init {
        fetchAllData()
    }

    // Function to fetch all data
    private fun fetchAllData() {
        viewModelScope.launch {
            fetchFoodsData()
            fetchEventsData()
            fetchDestinationsData()
        }
    }



    // Fetch events
    private suspend fun fetchEventsData() {
        _events.value = fetchEvents()
    }


    private suspend fun fetchFoodsData() {
        val foodsData = fetchFoods()
        Log.d("MainViewModel", "Fetched foods: $foodsData")
        _foods.value = foodsData
    }

    private suspend fun fetchDestinationsData() {
        val destinationsData = fetchDestinations()
        Log.d("MainViewModel", "Fetched destinations: $destinationsData")
        _destinations.value = destinationsData
    }
}