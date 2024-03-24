package com.fausto.datastore.querybreed

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QueryBreedIdManager @Inject constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val QUERY_BREED_ID = stringPreferencesKey("query_breed_id")
    }

    suspend fun saveQueryBreedId(queryBreedId: String) {
        context.dataStore.edit { settings ->
            settings[QUERY_BREED_ID] = queryBreedId
        }
    }

    fun getQueryBreedId(): Flow<String?> {
        return context.dataStore.data.map { settings ->
            settings[QUERY_BREED_ID]
        }
    }
}