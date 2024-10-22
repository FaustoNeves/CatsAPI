package com.fausto.datastore.querybreed

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.fausto.common.result.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface BreedIdsManager {
    suspend fun saveReferenceImageId(
        referenceImageId: String,
        queryBreedId: String
    ): ResultWrapper<Unit>

    fun getReferenceImageId(): ResultWrapper<Flow<String?>>
    fun getQueryBreedId(): ResultWrapper<Flow<String?>>
}