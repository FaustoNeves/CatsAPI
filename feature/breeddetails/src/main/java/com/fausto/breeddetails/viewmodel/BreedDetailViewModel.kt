package com.fausto.breeddetails.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeddetails.tracking.trackScreenView
import com.fausto.breeddetails.viewmodel.viewstate.BreedDetailViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.domain.usecase.GetBreedByIdUseCase
import com.fausto.domain.usecase.GetImagesByIdUseCase
import com.fausto.model.BreedImageModel
import com.fausto.model.BreedModel
import com.fausto.tracking.analytics.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val getBreedByIdUseCase: GetBreedByIdUseCase,
    private val getImagesByIdUseCase: GetImagesByIdUseCase,
    val analytics: Analytics
) : ViewModel() {

    private val _breedDetailViewState = MutableLiveData<BreedDetailViewState>()
    val breedDetailViewState: LiveData<BreedDetailViewState> get() = _breedDetailViewState

    init {
        _breedDetailViewState.value = BreedDetailViewState.InitialState
    }

    fun getBreedDetail(referenceImageId: String, breedId: String) {
        trackScreenView()
        viewModelScope.launch {
            _breedDetailViewState.value = BreedDetailViewState.Loading
            var getBreedByIdUseCaseData: BreedModel? = null
            var getImagesByIdUseCaseData: List<BreedImageModel>? = null
            when (val response = getBreedByIdUseCase.getBreedById(referenceImageId)) {
                is ResultWrapper.Success -> {
                    getBreedByIdUseCaseData = response.data
                }

                is ResultWrapper.Error -> {
                    _breedDetailViewState.value =
                        BreedDetailViewState.Error(response.exception?.message.toString())
                }
            }
            when (val response = getImagesByIdUseCase.getImagesById(breedId)) {
                is ResultWrapper.Success -> {
                    getImagesByIdUseCaseData = response.data
                }

                is ResultWrapper.Error -> {
                    _breedDetailViewState.value =
                        BreedDetailViewState.Error(response.exception?.message.toString())
                }
            }
            if (getBreedByIdUseCaseData != null && getImagesByIdUseCaseData != null) {
                _breedDetailViewState.value =
                    BreedDetailViewState.Success(getBreedByIdUseCaseData, getImagesByIdUseCaseData)
            }
        }
    }
}