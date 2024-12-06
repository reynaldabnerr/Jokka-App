package features.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import common.appbar.AppBar
import common.appbar.BottomBar
import common.cardScreen.EventCard
import common.filter.FilterSection
import common.loadingeffect.EventCardSkeleton
import data.Event
import data.fetchEvents

@Composable
fun EventScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf("event") }
    var eventList by remember { mutableStateOf<List<Event>>(emptyList()) }
    var filteredEventList by remember { mutableStateOf<List<Event>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        try {
            val fetchedEvents = fetchEvents() // Memanggil fungsi fetchEvents yang bersifat suspens
            eventList = fetchedEvents        // Menyimpan hasil ke state
            filteredEventList = fetchedEvents
        } catch (e: Exception) {
            e.printStackTrace()              // Menangkap dan mencetak error
        } finally {
            isLoading = false                // Mengatur loading menjadi false setelah selesai
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




