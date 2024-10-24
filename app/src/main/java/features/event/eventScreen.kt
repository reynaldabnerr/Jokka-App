package features.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jokka_app.R
import com.example.jokka_app.Screen
import common.appbar.AppBar
import common.appbar.BottomBar

data class Event(
    val imageResId: Int,
    val titleResId: Int,
    val date: String,
    val location: String,
    val description: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(navController: NavController) {
    var currentScreen by remember { mutableStateOf(Screen.Event.route) }

    val events = listOf(
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

    Scaffold(
        topBar = {
            AppBar(
                title = "Events"
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
                EventCard(event)
            }
        }
    }
}

@Composable
fun EventCard(event: Event) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = event.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = event.titleResId),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}