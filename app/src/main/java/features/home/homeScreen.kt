package features.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jokka_app.R
import common.appbar.AppBar
import common.appbar.BottomBar
import common.card.CategoryChip
import common.card.Food
import common.card.FoodCard
import common.card.Place
import common.card.PlaceHomeCard
import common.card.PopularSection
import common.carousel.Carousel
import kotlinx.coroutines.delay
import user.UserViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel()
) {
    val userData by userViewModel.userData.collectAsState()
    val name = userData.name.ifEmpty { "Unknown User" }

    val places = listOf(
        Place(R.drawable.place1, R.string.destination1, R.string.category1, R.string.category1),
        Place(R.drawable.place2, R.string.destination2, R.string.category2, R.string.category1),
        Place(R.drawable.place3, R.string.destination3, R.string.category3, R.string.category1),
        Place(R.drawable.place4, R.string.destination4, R.string.category4, R.string.category1),
        Place(R.drawable.place5, R.string.destination5, R.string.category5, R.string.category1)
    )

    val foods = listOf(
        Food(R.drawable.food1, R.string.food1, 4.9f, R.string.price1),
        Food(R.drawable.food2, R.string.food2, 4.8f, R.string.price2),
        Food(R.drawable.food3, R.string.food3, 4.95f, R.string.price3),
        Food(R.drawable.food4, R.string.food4, 4.7f, R.string.price4),
        Food(R.drawable.food5, R.string.food5, 4.2f, R.string.price5)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var isLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100) // Small delay for animation
        isLoaded = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFFF5F5F5)
                    )
                )
            )
    ) {
        AppBar(
            title = "Welcome"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .systemBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Welcome Section
            item {
                AnimatedVisibility(
                    visible = isLoaded,
                    enter = fadeIn() + slideInVertically()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Hello, $name",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.Gray
                        )
                        Text(
                            text = "Where to next?",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Featured Places Carousel
            item {
                Text(
                    text = "Featured Places",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Carousel(
                    places = places.take(3)
                )
            }

            // Categories Section
            item {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(places) { place ->
                        CategoryChip(place = place)
                    }
                }
            }

            // Popular Places Section
            item {
                PopularSection(
                    title = "Popular Places",
                    items = places,
                    itemContent = { place ->
                        PlaceHomeCard(
                            place = place,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                )
            }

            // Food Section
            item {
                PopularSection(
                    title = "Popular Foods",
                    items = foods,
                    itemContent = { food ->
                        FoodCard(food = food)
                    }
                )
            }
        }

        BottomBar(
            currentScreen = currentRoute ?: "",
            navController = navController,
            onItemSelected = { selectedScreen ->
                if (selectedScreen != currentRoute) {
                    navController.navigate(selectedScreen)
                }
            }
        )
    }
}