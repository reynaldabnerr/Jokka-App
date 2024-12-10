package features.home


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jokka_app.Screen
import component.appbar.AppBar
import component.appbar.BottomBar
import component.cardHome.DestinationHomeCard
import component.cardHome.EventHomeCard
import component.cardHome.FoodHomeCard
import component.cardHome.PopularSection
import component.carousel.Carousel
import data.MainViewModel
import kotlinx.coroutines.delay
import data.UserViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel = viewModel(),
    mainViewModel: MainViewModel = viewModel()
) {
    val userData by userViewModel.userData.collectAsState()
    val name = userData.name.ifEmpty { "Unknown User" }

    // Mengambil data dari MainViewModel
    val foods by mainViewModel.foods.collectAsState()
    val events by mainViewModel.events.collectAsState()
    val destinations by mainViewModel.destinations.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var isLoaded by remember { mutableStateOf(false) }

    // Animasi ketika data telah dimuat
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
                        Color(0xFFF3F3F3),
                        Color(0xFFDCDCDC)
                    )
                )
            )
    ) {
        AppBar(
            title = "Visit Makassar",
            modifier = Modifier.shadow(4.dp)
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
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFFFF2111), // Merah muda
                                        Color(0xFFFA8F8F), // Pink lembut
                                        Color(0xFF5875FF)  // Pink terang
                                    )
                                ),
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Hello, $name",
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        Text(
                            text = "Welcome to Makassar !!!",
                            fontFamily = FontFamily.SansSerif,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.Unspecified
                        )
                    }
                }
            }

            // Featured Places Carousel
            item {
                if (destinations.isNotEmpty()) {
                    Carousel(
                        destinations = destinations.take(3)
                    )
                } else {
                    Text("Loading places...", color = Color.Gray)
                }
            }

            // Popular Places Section
            item {
                Text(
                    text = "Discover Popular Places",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                PopularSection(
                    title = "Top Destinations to Explore",
                    items = destinations.take(4),
                    onClickSeeAll = {
                        navController.navigate(Screen.Destination.route)
                    },
                    itemContent = { destination ->
                        DestinationHomeCard(
                            destination = destination,
                            modifier = Modifier.fillMaxWidth(),
                            navController = navController
                        )
                    }
                )
            }

            // Food Section
            item {
                Text(
                    text = "Savor Delicious Foods",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                PopularSection(
                    title = "Top Delicacies You Can’t Miss",
                    items = foods.take(4),
                    onClickSeeAll = {
                        navController.navigate(Screen.Food.route)
                    },
                    itemContent = { food ->
                        FoodHomeCard(food = food,
                            modifier = Modifier.fillMaxWidth(),
                            navController = navController)
                    }
                )
            }

            // Events Section
            item {
                Text(
                    text = "Upcoming Events",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
                PopularSection(
                    title = "Makassar’s Event Vibes",
                    items = events.take(4),
                    onClickSeeAll = {
                        navController.navigate(Screen.Event.route)
                    },
                    itemContent = { event ->
                        EventHomeCard(
                            event = event,
                            modifier = Modifier.fillMaxWidth(),
                            navController = navController
                        )
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
            },
        )
    }
}