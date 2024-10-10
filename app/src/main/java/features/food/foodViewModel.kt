import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokka_app.R
import common.cardScreen.ModernFood
import features.food.FoodPreferencesDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {
    private val foodPreferencesDataStore = FoodPreferencesDataStore(application)

    private val _foods = MutableStateFlow<List<ModernFood>>(emptyList())
    val foods: StateFlow<List<ModernFood>> = _foods.asStateFlow()

    private val _likedFoodIds = MutableStateFlow<Set<Int>>(emptySet())
    val likedFoodIds: StateFlow<Set<Int>> = _likedFoodIds.asStateFlow()

    init {
        loadFoods()
        viewModelScope.launch {
            foodPreferencesDataStore.likedFoodIdsFlow.collectLatest { likedIds ->
                _likedFoodIds.value = likedIds
            }
        }
    }

    private fun loadFoods() {
        val foodList = listOf(
            ModernFood(1, R.drawable.food1, R.string.food1, 4.9f, R.string.price1, R.string.food_description1),
            ModernFood(2, R.drawable.food2, R.string.food2, 4.8f, R.string.price2, R.string.food_description2),
            ModernFood(3, R.drawable.food3, R.string.food3, 4.95f, R.string.price3, R.string.food_description3),
            ModernFood(4, R.drawable.food4, R.string.food4, 4.7f, R.string.price4, R.string.food_description4),
            ModernFood(5, R.drawable.food5, R.string.food5, 4.85f, R.string.price5, R.string.food_description5)
        )
        _foods.value = foodList
    }

    fun toggleLike(foodId: Int) {
        viewModelScope.launch {
            val currentLikedIds = _likedFoodIds.value.toMutableSet()
            if (currentLikedIds.contains(foodId)) {
                currentLikedIds.remove(foodId)
            } else {
                currentLikedIds.add(foodId)
            }
            _likedFoodIds.value = currentLikedIds
            foodPreferencesDataStore.updateLikedFoodIds(currentLikedIds)
        }
    }

    fun isLiked(foodId: Int): Boolean {
        return _likedFoodIds.value.contains(foodId)
    }
}