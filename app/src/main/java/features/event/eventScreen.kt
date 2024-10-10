//package features.event
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.jokka_app.R
//import common.appbar.AppBar
//import common.appbar.BottomBar
//import common.card.Event
//import common.card.EventCard
//
//@Composable
//fun EventScreen(
//    navController: NavController
//) {
//    val events = listOf(
//        Event(R.drawable.event1, R.string.event1, R.string.eventdate1),
//        Event(R.drawable.event2, R.string.event2, R.string.eventdate2),
//        Event(R.drawable.event3, R.string.event3, R.string.eventdate3),
//        Event(R.drawable.event4, R.string.event4, R.string.eventdate4),
//        Event(R.drawable.event5, R.string.event5, R.string.eventdate5)
//    )
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFFFFFFFF),
//                        Color(0xFFF5F5F5)
//                    )
//                )
//            )
//    ) {
//        AppBar(title = "Events")
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .weight(1f)
//                .systemBarsPadding()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            item {
//                Text(
//                    text = "Upcoming Events",
//                    style = MaterialTheme.typography.headlineMedium,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//            }
//
//            items(events) { event ->
//                EventCard(event = event)
//            }
//        }
//
//        BottomBar(
//            currentScreen = "event",
//            navController = navController,
//            onItemSelected = { selectedScreen ->
//                if (selectedScreen != "event") {
//                    navController.navigate(selectedScreen)
//                }
//            }
//        )
//    }
//}