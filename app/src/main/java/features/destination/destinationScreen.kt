package features.destination

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import common.appbar.AppBar
import common.appbar.BottomBar
import common.cardScreen.PlaceCard

@Composable
fun DestinationScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel: DestinationViewModel = viewModel(
        factory = ViewModelFactory(context.applicationContext as Application)
    )

    val destinations by viewModel.destinations.collectAsState()
    val likedDestinationIds by viewModel.likedDestinationIds.collectAsState()

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
        AppBar(title = "Destinations")

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .systemBarsPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Explore Amazing Places",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            items(destinations) { destination ->
                PlaceCard(
                    destination = destination,  // Changed from place to destination
                    isLiked = likedDestinationIds.contains(destination.id),
                    onLikeClick = { viewModel.toggleLike(destination.id) }
                )
            }
        }

        BottomBar(
            currentScreen = "destination",
            navController = navController,
            onItemSelected = { selectedScreen ->
                if (selectedScreen != "destination") {
                    navController.navigate(selectedScreen)
                }
            }
        )
    }
}

class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DestinationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DestinationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}