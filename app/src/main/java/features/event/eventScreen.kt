package features.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import common.filter.FilterSection
import common.loadingeffect.EventCardSkeleton
import kotlinx.coroutines.tasks.await
import java.text.NumberFormat

@Composable
fun EventScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf("event") }
    var eventList by remember { mutableStateOf<List<EventData>>(emptyList()) }
    var filteredEventList by remember { mutableStateOf<List<EventData>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        try {
            val fetchedEvents = fetchEventsFromFirestore()
            eventList = fetchedEvents
            filteredEventList = fetchedEvents
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(title = "Events")
        },
        bottomBar = {
            BottomBar(
                currentScreen = currentScreen,
                navController = navController,
                onItemSelected = { route -> currentScreen = route }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Filter Section
            FilterSection(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                    filteredEventList = if (category == "All") {
                        eventList
                    } else {
                        eventList.filter { it.eventcategories == category }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(6) { // Skeleton untuk 6 item
                        EventCardSkeleton()
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredEventList) { event ->
                        EventCard(
                            event = event,
                            isLoading = false,
                            onClick = {
                                navController.navigate("event_details/${event.eventname}")
                            }
                        )
                    }
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

@Composable
fun EventCard(
    event: EventData?,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Bagian Gambar Event
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                    )
                } else {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = event?.eventimage,
                            placeholder = painterResource(id = com.example.jokka_app.R.drawable.logo_jokka_app),
                            error = painterResource(id = com.example.jokka_app.R.drawable.logo_jokka_app)
                        ),
                        contentDescription = "Event Image",
                        contentScale = ContentScale.Crop, // Pastikan ukuran gambar seragam
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bagian Informasi Event
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                // Nama Event
                Text(
                    text = if (isLoading) "" else event?.eventname ?: "",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    modifier = Modifier
                        .background(if (isLoading) Color.LightGray else Color.Transparent)
                        .padding(bottom = 8.dp)
                )

                // Lokasi Event
                Text(
                    text = if (isLoading) "" else event?.eventlocation ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .background(if (isLoading) Color.LightGray else Color.Transparent)
                        .padding(bottom = 8.dp)
                )

                // Harga Event
                val formattedPrice = NumberFormat.getNumberInstance(java.util.Locale("id", "ID"))
                    .format(event?.eventprice ?: 0)
                Text(
                    text = if (isLoading) "" else "Price: Rp $formattedPrice",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier
                        .background(if (isLoading) Color.LightGray else Color.Transparent)
                        .padding(bottom = 8.dp)
                )

                // Tanggal Event
                Text(
                    text = if (isLoading) "" else event?.eventdate ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    modifier = Modifier.background(if (isLoading) Color.LightGray else Color.Transparent)
                )
            }
        }
    }
}

