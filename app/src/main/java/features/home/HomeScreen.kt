package features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jokka_app.R
import common.appbar.AppBar
import common.bottombar.BottomBar
import common.card.Place
import common.card.PlaceCard
import common.carousel.Carousel

@Composable
fun HomeScreen() {
    val places = listOf(
        Place(R.drawable.image1, R.string.travel1),
        Place(R.drawable.image2, R.string.travel2),
        Place(R.drawable.image3, R.string.travel3),
        Place(R.drawable.image4, R.string.travel4),
        Place(R.drawable.image5, R.string.travel5)
    )

    var selectedScreen by remember { mutableStateOf("Home") }

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
            currentScreen = selectedScreen,
            onItemSelected = { selectedScreen = it }
        )
    }
}