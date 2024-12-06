package features.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import common.appbar.AppBar
import common.appbar.BottomBar
import kotlinx.coroutines.tasks.await

@Composable
fun EventScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf("event") }

    // State untuk menyimpan list event yang di-fetch dari Firestore
    var eventList by remember { mutableStateOf<List<EventData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        // Fetch data dari Firestore
        try {
            val fetchedEvents = fetchEventsFromFirestore()
            eventList = fetchedEvents
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(
                title = "Events",
            )
        },
        bottomBar = {
            BottomBar(
                currentScreen = currentScreen,
                navController = navController,
                onItemSelected = { route -> currentScreen = route }
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Grid 2 kolom
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(eventList) { event ->
                    EventCard(
                        event = event,
                        onClick = {
                            // Navigate to event details screen
                            navController.navigate("event_details/${event.eventname}")
                        }
                    )
                }
            }
        }
    }
}

// Fungsi untuk fetch data dari Firestore
suspend fun fetchEventsFromFirestore(): List<EventData> {
    val firestore = FirebaseFirestore.getInstance()
    val eventCollection = firestore.collection("event")
    val snapshot = eventCollection.get().await()

    return snapshot.documents.mapNotNull { document ->
        document.toObject(EventData::class.java)
    }
}

// Data class untuk merepresentasikan event
data class EventData(
    val eventcategories: String = "",
    val eventdate: String = "",
    val eventdescription: String = "",
    val eventid: String = "",
    val eventimage: String = "",
    val eventlocation: String = "",
    val eventname: String = "",
    val eventprice: Int = 0
)

// Card untuk event dengan styling baru
@Composable
fun EventCard(
    event: EventData,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp)), // Sudut membulat pada seluruh kartu
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Elevasi untuk efek bayangan
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Bagian Gambar Event
            Image(
                painter = rememberAsyncImagePainter(
                    model = event.eventimage,
                    placeholder = painterResource(id = com.example.jokka_app.R.drawable.logo_jokka_app),
                    error = painterResource(id = com.example.jokka_app.R.drawable.logo_jokka_app)
                ),
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)), // Sudut membulat hanya di bagian atas
                contentScale = ContentScale.Crop
            )

            // Spacer antara gambar dan konten
            Spacer(modifier = Modifier.height(8.dp))

            // Bagian Informasi Event
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp) // Padding untuk isi
            ) {
                // Nama Event
                Text(
                    text = event.eventname,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2, // Batasi nama menjadi maksimal 2 baris
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Lokasi Event
                Text(
                    text = event.eventlocation,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Harga Event
                Text(
                    text = "Price: Rp ${event.eventprice}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Tanggal Event
                Text(
                    text = event.eventdate,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}