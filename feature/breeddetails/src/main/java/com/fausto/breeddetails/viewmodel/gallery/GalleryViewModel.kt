package com.fausto.breeddetails.viewmodel.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeddetails.tracking.gallery.trackScreenView
import com.fausto.breeddetails.viewmodel.gallery.interact.GalleryInteract
import com.fausto.breeddetails.viewmodel.gallery.viewstate.GalleryViewState
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

    private val _galleryViewState = MutableLiveData<GalleryViewState>()
    val galleryViewState: LiveData<GalleryViewState> = _galleryViewState

    fun interpret(interaction: GalleryInteract) {
        when (interaction) {
            is GalleryInteract.ViewCrated -> getImagesById()
            is GalleryInteract.OnErrorAction -> getImagesById()
        }
    }

    private fun getImagesById() {
        trackScreenView()
        viewModelScope.launch {
            _galleryViewState.postValue(GalleryViewState.Loading)
            breedIdsManager.getQueryBreedId().catch { exception ->
                _galleryViewState.postValue(exception.message?.let { GalleryViewState.Error(it) })
            }.collect { queryBreedId ->
                when (val result = queryBreedId?.let { getImagesByIdUseCase.getImagesById(it) }) {
                    is ResultWrapper.Success -> {
                        _galleryViewState.postValue(result.data.let { GalleryViewState.Success(it) })
                    }

                    is ResultWrapper.Error -> {
                        _galleryViewState.postValue(GalleryViewState.Error(result.exception?.message.toString()))
                    }

                    null -> _galleryViewState.postValue(GalleryViewState.Error())
                }
            }
        }
    }
}