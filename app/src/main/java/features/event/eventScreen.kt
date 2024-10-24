// EventScreen.kt

package features.event

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jokka_app.R
import com.example.jokka_app.Screen
import common.appbar.AppBar
import common.appbar.BottomBar
import common.cardScreen.Event
import common.cardScreen.EventCard

@Composable
fun EventScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf(Screen.Event.route) }

    val events = remember {
        listOf(
            Event(
                R.drawable.events1,
                R.string.event1,
                "28 Oct 2024",
                "Fort Rotterdam",
                "F8 Makassar"
            ),
            Event(
                R.drawable.events2,
                R.string.event2,
                "5 Nov 2024",
                "Pantai Losari",
                "Jokka-Jokka Makassar"
            ),
            Event(
                R.drawable.events3,
                R.string.event3,
                "12 Nov 2024",
                "Trans Studio",
                "Music Concert"
            )
        )
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(events) { event ->
                EventCard(
                    event = event,
                    onClick = {
                        // Navigate to event details screen
                        navController.navigate("event_details/${event.titleResId}")
                    }
                )
            }
        }
    }
}