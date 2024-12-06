package data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
        snapshot.documents.mapNotNull { it.toObject(Event::class.java) }
    } catch (e: Exception) {
        e.printStackTrace()
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

class DestinationViewModel : ViewModel() {

    private val _destinations = MutableStateFlow<List<Destination>>(emptyList())
    val destinations: StateFlow<List<Destination>> = _destinations

    init {
        fetchAllDestinations()
    }

    private fun fetchAllDestinations() {
        viewModelScope.launch {
            _destinations.value = fetchDestinations()
        }
    }
}