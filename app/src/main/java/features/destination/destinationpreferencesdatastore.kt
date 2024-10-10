package features.destination

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "destination_preferences")

class DestinationPreferencesDataStore(private val context: Context) {
    private val likedDestinationIdsKey = stringPreferencesKey("liked_destination_ids")

    val likedDestinationIdsFlow: Flow<Set<Int>> = context.dataStore.data
        .map { preferences ->
            preferences[likedDestinationIdsKey]?.split(",")?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        }

    suspend fun updateLikedDestinationIds(likedDestinationIds: Set<Int>) {
        context.dataStore.edit { preferences ->
            preferences[likedDestinationIdsKey] = likedDestinationIds.joinToString(",")
        }
    }
}