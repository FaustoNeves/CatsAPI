package com.fausto.datastore.querybreed

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fausto.datastore.constants.DatastoreConstants
import com.fausto.datastore.constants.DatastoreConstants.SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreedIdsManager @Inject constructor(private val context: Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)

    companion object {
        val REFERENCE_IMAGE_ID = stringPreferencesKey(DatastoreConstants.REFERENCE_IMAGE_ID)
        val QUERY_BREED_ID = stringPreferencesKey(DatastoreConstants.QUERY_BREED_ID)
    }

    suspend fun saveReferenceImageId(referenceImageId: String, queryBreedId: String) {
        context.dataStore.apply {
            edit { settings ->
                settings[REFERENCE_IMAGE_ID] = referenceImageId
            }
            edit { settings ->
                settings[QUERY_BREED_ID] = queryBreedId
            }
        }
    }

    fun getReferenceImageId(): Flow<String?> {
        return context.dataStore.data.map { settings ->
            settings[REFERENCE_IMAGE_ID]
        }
    }

    fun getQueryBreedId(): Flow<String?> {
        return context.dataStore.data.map { settings ->
            settings[QUERY_BREED_ID]
        }
    }
}