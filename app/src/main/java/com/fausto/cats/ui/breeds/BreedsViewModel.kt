package com.fausto.cats.ui.breeds

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.cats.domain.model.SectionModel
import com.fausto.cats.domain.usecase.GetBreedByIdUseCase
import com.fausto.cats.domain.usecase.GetBreedsUseCase
import com.fausto.cats.domain.usecase.GetImagesByIdUseCase
import com.fausto.cats.ui.details.BreedDetailViewState
import com.fausto.cats.ui.details.ImageViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreedsViewModel @Inject constructor(
    private val useCase: GetBreedsUseCase,
    private val getBreedsUseCase: GetBreedByIdUseCase,
    private val getImageUseCase: GetImagesByIdUseCase
) : ViewModel() {

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> = _breedsViewState

    fun getBreeds() {
        _breedsViewState.value = BreedsViewState.Loading
        viewModelScope.launch {
            try {
                val response = useCase.getBreeds()
                val sectionModelsList = SectionModel(breedsList = response).buildSections(response)
                _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
            } catch (e: Exception) {
                _breedsViewState.value = BreedsViewState.Error("An error occurred: ${e.message}")
            }
        }
    }

    private val _breedDetailViewState = MutableLiveData<BreedDetailViewState>()
    val breedDetailViewState: LiveData<BreedDetailViewState> = _breedDetailViewState

    fun getBreedDetail() {
        viewModelScope.launch {
            try {
                val response = getBreedsUseCase.getBreedById("0XYvRd7oD")
                _breedDetailViewState.value = BreedDetailViewState.Success(response)
            } catch (exception: Exception) {
                _breedDetailViewState.value =
                    BreedDetailViewState.Error("An error occurred: ${exception.message}")
            }
        }
    }

    private val _imageViewState = MutableLiveData<ImageViewState>()
    val imageViewState: LiveData<ImageViewState> = _imageViewState

    fun getImage(breedId: String) {
        viewModelScope.launch {
            try {
                val response = getImageUseCase.getImagesById(breedId)
                _imageViewState.value = ImageViewState.Success(response)
            } catch (exception: Exception) {
                _imageViewState.value = ImageViewState.Error("error: ${exception.message}")
            }
        }
    }
}