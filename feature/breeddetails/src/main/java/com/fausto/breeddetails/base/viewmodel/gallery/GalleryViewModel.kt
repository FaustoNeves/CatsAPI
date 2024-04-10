package com.fausto.breeddetails.base.viewmodel.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeddetails.base.viewmodel.gallery.interact.GalleryInteract
import com.fausto.common.result.ResultWrapper
import com.fausto.common.result.getResult
import com.fausto.datastore.querybreed.BreedIdsManager
import com.fausto.domain.usecase.GetImagesByIdUseCase
import com.fausto.tracking.analytics.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GalleryViewModel @Inject constructor(
    private val getImagesByIdUseCase: GetImagesByIdUseCase,
    private val breedIdsManager: BreedIdsManager,
    val analytics: Analytics
) : ViewModel() {

    fun interpret(interaction: GalleryInteract) {
        when (interaction) {
            is GalleryInteract.ViewCrated -> getImagesById()
        }
    }

    private fun getImagesById() {
        viewModelScope.launch {
            val queryBreedId = breedIdsManager.getQueryBreedId().catch { exception ->
                //Handle error
            }.collect { queryBreedId ->
                when (val result = getResult {
                    queryBreedId?.let { getImagesByIdUseCase.getImagesById(it) }
                }) {
                    is ResultWrapper.Success -> {

                    }

                    is ResultWrapper.Error -> {

                    }
                }
            }
        }
    }
}