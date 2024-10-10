package features.food

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Use camelCase for the property name
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "food_preferences")

class FoodPreferencesDataStore(private val context: Context) {
    private val likedFoodIdsKey = stringPreferencesKey("liked_food_ids") // Updated property name

    val likedFoodIdsFlow: Flow<Set<Int>> = context.dataStore.data
        .map { preferences ->
            preferences[likedFoodIdsKey]?.split(",")?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        }

    suspend fun updateLikedFoodIds(likedFoodIds: Set<Int>) {
        context.dataStore.edit { preferences ->
            preferences[likedFoodIdsKey] = likedFoodIds.joinToString(",")
        }
    }
}