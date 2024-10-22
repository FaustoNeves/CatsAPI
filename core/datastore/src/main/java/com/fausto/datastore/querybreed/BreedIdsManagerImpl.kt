package com.fausto.datastore.querybreed

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.constants.DatastoreConstants
import com.fausto.datastore.constants.DatastoreConstants.SETTINGS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreedIdsManagerImpl @Inject constructor(private val context: Context) : BreedIdsManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS)

    companion object {
        val REFERENCE_IMAGE_ID = stringPreferencesKey(DatastoreConstants.REFERENCE_IMAGE_ID)
        val QUERY_BREED_ID = stringPreferencesKey(DatastoreConstants.QUERY_BREED_ID)
    }

    override suspend fun saveReferenceImageId(
        referenceImageId: String, queryBreedId: String
    ): ResultWrapper<Unit> {
        return try {
            context.dataStore.apply {
                edit { settings ->
                    settings[REFERENCE_IMAGE_ID] = referenceImageId
                }
                edit { settings ->
                    settings[QUERY_BREED_ID] = queryBreedId
                }
            }
            ResultWrapper.Success(Unit)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }

    override fun getReferenceImageId(): ResultWrapper<Flow<String?>> {
        return try {
            val flow = context.dataStore.data.map { settings ->
                settings[REFERENCE_IMAGE_ID]
            }
            ResultWrapper.Success(flow)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }

    override fun getQueryBreedId(): ResultWrapper<Flow<String?>> {
        return try {
            val flow = context.dataStore.data.map { settings ->
                settings[QUERY_BREED_ID]
            }
            ResultWrapper.Success(flow)
        } catch (exception: Exception) {
            ResultWrapper.Error(exception)
        }
    }
}