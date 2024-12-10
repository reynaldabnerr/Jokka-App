package data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

    // State for Selected Event
    private val _selectedEvent = MutableStateFlow<Event?>(null)
    val selectedEvent: StateFlow<Event?> = _selectedEvent

    private val _selectedFood = MutableStateFlow<Food?>(null)
    val selectedFood: StateFlow<Food?> = _selectedFood

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

    // Fetch Events
    private suspend fun fetchEventsData() {
        _events.value = fetchEvents()
    }

    // Fetch Foods
    private suspend fun fetchFoodsData() {
        val foodsData = fetchFoods()
        Log.d("MainViewModel", "Fetched foods: $foodsData")
        _foods.value = foodsData
    }

    // Fetch Destinations
    private suspend fun fetchDestinationsData() {
        val destinationsData = fetchDestinations()
        Log.d("MainViewModel", "Fetched destinations: $destinationsData")
        _destinations.value = destinationsData
    }

    // Function to load event details by ID
    fun loadEventDetails(eventId: String) {
        viewModelScope.launch {
            val event = _events.value.find { it.eventid == eventId }
            if (event != null) {
                Log.d("ViewModel", "Event found: $event")
            } else {
                Log.e("ViewModel", "Event not found for ID: $eventId")
            }
            _selectedEvent.value = event
        }
    }

    fun loadFoodDetails(foodId: String) {
        viewModelScope.launch {
            val food = _foods.value.find { it.foodid == foodId }
            if (food != null) {
                Log.d("ViewModel", "Food found: $food")
            } else {
                Log.e("ViewModel", "Food not found for ID: $foodId")
            }
            _selectedFood.value = food
        }
    }
}

// Fetch Foods
suspend fun fetchFoods(): List<Food> {
    val db = Firebase.firestore
    return try {
        val snapshot = db.collection("food").get().await()
        snapshot.documents.mapNotNull { it.toObject(Food::class.java) }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList() // Jika gagal, kembalikan list kosong
    }
}

// Fetch Events
suspend fun fetchEvents(): List<Event> {
    val db = Firebase.firestore
    return try {
        val snapshot = db.collection("event").get().await()
        Log.d("Firestore", "Fetched events: ${snapshot.documents.size}")
        snapshot.documents.mapNotNull {
            val event = it.toObject(Event::class.java)
            Log.d("Firestore", "Event: $event")
            event
        }
    } catch (e: Exception) {
        Log.e("Firestore", "Error fetching events", e)
        emptyList() // Jika gagal, kembalikan list kosong
    }
}

// Fetch Destinations
suspend fun fetchDestinations(): List<Destination> {
    val db = Firebase.firestore
    return try {
        val snapshot = db.collection("destination").get().await()
        snapshot.documents.mapNotNull { it.toObject(Destination::class.java) }
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList() // Jika gagal, kembalikan list kosong
    }
}

// Data Classes
data class Food(
    val foodcategories: String = "",
    val fooddesc: String = "",
    val foodid: String = "",
    val foodimage: String = "",
    val foodname: String = "",
    val foodprice: Int = 0,
    val foodrating: String = ""
)

data class Event(
    val eventcategories: String = "",
    val eventdate: String = "",
    val eventdescription: String = "",
    val eventid: String = "",
    val eventimage: String = "",
    val eventlocation: String = "",
    val eventname: String = "",
    val eventprice: Int = 0
)

data class Destination(
    val destinationcategory: String = "",
    val destinationdescription: String = "",
    val destinationid: String = "",
    val destinationimage: String = "",
    val destinationlocation: String = "",
    val destinationname: String = "",
    val destinationprice: Int = 0,
    val destinationrating: String=""
)