import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jokka_app.Screen
import component.appbar.AppBar
import component.appbar.BottomBar
import component.cardScreen.FoodCard
import data.MainViewModel

@Composable
fun FoodScreen(
    navController: NavController,
    mainViewModel: MainViewModel = viewModel()
) {
    val foods by mainViewModel.foods.collectAsState()

    Scaffold(
        topBar = {
            AppBar(title = "Foods")
        },
        bottomBar = {
            BottomBar(
                currentScreen = Screen.Food.route,
                navController = navController,
                onItemSelected = { selectedScreen ->
                    if (selectedScreen != Screen.Food.route) {
                        navController.navigate(selectedScreen)
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(foods) { food ->
                FoodCard(
                    food = food,
                    navController= navController
                )
            }
        }
    }
}