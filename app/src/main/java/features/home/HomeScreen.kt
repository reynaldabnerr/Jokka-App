package features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jokka_app.R
import common.appbar.AppBar
import common.appbar.BottomBar
import common.card.Place
import common.card.PlaceCard
import common.carousel.Carousel

@Composable
fun HomeScreen(navController: NavController) { // Pass NavController as a parameter
    val places = listOf(
        Place(R.drawable.image1, R.string.travel1),
        Place(R.drawable.image2, R.string.travel2),
        Place(R.drawable.image3, R.string.travel3),
        Place(R.drawable.image4, R.string.travel4),
        Place(R.drawable.image5, R.string.travel5)
    )

    // Get the current route from the NavController's back stack
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TopAppBar at the top of the screen
        AppBar(
            title = "Home",
            onNavigationIconClick = {
                // Handle back button click if needed
            }
        )

        // LazyColumn for scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .systemBarsPadding() // Safe area for entire layout
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // First item: Carousel
            item {
                Carousel(places = places)
            }

            // Spacer to give space between the Carousel and PlaceCard
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Main Box for "Place To Visit"
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.DarkGray)
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Place To Visit",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // LazyRow for displaying the list of places
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp)
                        ) {
                            items(places) { place ->
                                PlaceCard(place = place)
                            }
                        }
                    }
                }
            }
        }

        // Bottom Navigation Bar
        BottomBar(
            currentScreen = currentRoute ?: "", // Use the current route dynamically
            navController = navController, // Pass NavController to BottomBar
            onItemSelected = { selectedScreen ->
                if (selectedScreen != currentRoute) {
                    navController.navigate(selectedScreen)
                }
            }
        )
    }
}